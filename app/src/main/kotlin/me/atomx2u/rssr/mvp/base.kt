package me.atomx2u.rssr.mvp

import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.internal.functions.Functions
import java.lang.ref.WeakReference
import java.util.*

abstract class BasePresenter<View : MvpView>(view: View) : MvpPresenter {
    val view: WeakReference<View> = WeakReference(view)
    val viewActionQueue = RxViewActionQueue(AndroidSchedulers.mainThread())

    override fun create() {
    }

    override fun resume() {
        viewActionQueue.resume()
    }

    override fun pause() {
        viewActionQueue.pause()
    }

    override fun destroy() {
        viewActionQueue.destroy()
    }

    override fun back() {
    }

    fun <T> WeakReference<T>.callIfNotNull(block: T.() -> Unit) = get()?.let(block)
}

abstract class BaseActivity<VView: MvpView, Presenter : MvpPresenter>
    : AppCompatActivity(), MvpNexus {

    lateinit var vView: VView
    lateinit var presenter: Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        presenter = presenter()
        presenter.create()
    }

    override fun onResume() {
        super.onResume()
        presenter.resume()
    }

    override fun onPause() {
        super.onPause()
        presenter.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments
            ?.reversed()
            ?.find { it.isVisible && it is MvpNexus }
            ?.let { (it as BaseFragment<*, *>).onBack() }
            ?:super.onBackPressed()
    }

    final override fun onBack() = onBackPressed()

    abstract val layoutRes: Int
    abstract fun vView(): VView
    abstract fun presenter(): Presenter
}

/**
 * BaseFragment 并不是一个 View，而是一个 P, V 关系的联结(nexus)。
 * */
abstract class BaseFragment<VView : MvpView, Presenter : MvpPresenter>
    : Fragment(), MvpNexus {

    lateinit var vView: VView
    lateinit var presenter: Presenter

    private lateinit var unbinder : Unbinder

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutRes, container, false).apply {
            unbinder = ButterKnife.bind(this@BaseFragment, this)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vView = vView()
        presenter = presenter(context!!)
        presenter.create()
    }

    override fun onResume() {
        super.onResume()
        presenter.resume()
    }

    override fun onPause() {
        super.onPause()
        presenter.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder.unbind()
    }

    override fun onBack() {
        presenter.back()
    }

    abstract val layoutRes: Int
    abstract fun vView(): VView
    abstract fun presenter(context: Context): Presenter
}

// TODO 变扭
abstract class BaseDialogFragment<VView: MvpView, Presenter: MvpPresenter>
    : DialogFragment(), MvpNexus {

    lateinit var vView: VView
    lateinit var presenter: Presenter

    private lateinit var unbinder : Unbinder

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutRes, container, false).apply {
            unbinder = ButterKnife.bind(this@BaseDialogFragment, this)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vView = vView()
        presenter = presenter(context!!)
        presenter.create()
    }

    override fun onResume() {
        super.onResume()
        presenter.resume()
    }

    override fun onPause() {
        super.onPause()
        presenter.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder.unbind()
    }

    override fun onBack() {
        presenter.back()
    }

    abstract val layoutRes: Int
    abstract fun vView(): VView
    abstract fun presenter(context: Context): Presenter
}


// ViewActionQueue for preventing the onNewFeedAdded FragmentTransaction after onSaveInstanceState()
// 阻止进一步的操作（UI操作过程再次启动新的后台线程操作）
class RxViewActionQueue(
    private val uiScheduler: Scheduler
) {

    val queue = LinkedList<Action>()

    private val compositeDisposable = CompositeDisposable()

    var isPaused = false

    fun resume() {
        isPaused = false
        synchronized(queue) {
            queue.forEach(Action::run)
            queue.clear()
        }
    }

    fun pause() {
        isPaused = true
    }

    fun destroy() {
        compositeDisposable.dispose()
    }

    // 缺点： 如果有 error log， 在 pause 时也将延迟；可以将 error log 放在 doOnError 里
    fun <T> subscribeTo(
        observable: Observable<T>,
        onNext: Consumer<in T> = Functions.emptyConsumer(),
        onError: Consumer<in Throwable> = Functions.emptyConsumer(),
        onComplete: Action = Functions.EMPTY_ACTION,
        onSubscribe: Consumer<in Disposable> = Functions.emptyConsumer()
    ) {
        compositeDisposable.add(
            observable.observeOn(uiScheduler).subscribe(
                QueueConsumer(onNext),
                QueueConsumer(onError),
                QueueAction(onComplete),
                onSubscribe)
        )
    }

    fun <T> subscribeTo(
        single: Single<T>,
        onSuccess: Consumer<in T> = Functions.emptyConsumer(),
        onError: Consumer<in Throwable> = Functions.emptyConsumer()
    ) {
        compositeDisposable.add(
            single.observeOn(uiScheduler).subscribe(QueueConsumer(onSuccess), QueueConsumer(onError))
        )
    }

    fun subscribeTo(
        completable: Completable,
        onComplete: Action = Functions.EMPTY_ACTION,
        onError: Consumer<in Throwable> = Functions.emptyConsumer()
    ) {
        completable.observeOn(uiScheduler).subscribe(QueueAction(onComplete), QueueConsumer(onError))
    }

    inner class QueueConsumer<T>(private val source: Consumer<T>) : Consumer<T> {
        override fun accept(t: T) {
            if (isPaused) {
                synchronized(queue) {
                    queue.add(Action {
                        source.accept(t)
                    })
                }
            } else {
                source.accept(t)
            }
        }
    }

    inner class QueueAction(private val source: Action): Action {
        override fun run() {
            if (isPaused) {
                synchronized(queue) {
                    queue.add(source)
                }
            } else {
                source.run()
            }
        }
    }

}
