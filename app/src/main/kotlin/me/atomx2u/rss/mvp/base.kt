package me.atomx2u.rss.mvp

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import java.lang.ref.WeakReference

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

abstract class BaseActivity<Presenter : MvpPresenter>
    : AppCompatActivity(), MvpNexus {

    lateinit var presenter: Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = newPresenter()
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
        supportFragmentManager.fragments?.forEach {
            supportFragmentManager
                .beginTransaction()
                .remove(it)
                .commit()
        }
        supportFragmentManager.executePendingTransactions()
        presenter.destroy()
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments
            ?.reversed()
            ?.find { it.isVisible && it is MvpNexus }
            ?.let { (it as BaseFragment<*>).onBack() }
            ?:super.onBackPressed()
    }

    final override fun onBack() = onBackPressed()

    abstract fun newPresenter(): Presenter
}

/**
 * BaseFragment 并不是一个 View，而是一个 P, V 关系的联结(nexus)。
 * 可以将 BaseFragment 同时作为一个 View 使用；也可以单独实现一个 MvpView
 * */
abstract class BaseFragment<Presenter : MvpPresenter>
    : Fragment(), MvpNexus {

    lateinit var presenter: Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = newPresenter()
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

    override fun onBack() {
        presenter.back()
    }

    abstract fun newPresenter(): Presenter
}


abstract class BaseDialogFragment<Presenter: MvpPresenter>
    : DialogFragment(), MvpNexus {

    lateinit var presenter: Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = newPresenter()
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

    override fun onBack() {
        presenter.back()
    }

    abstract fun newPresenter(): Presenter
}
