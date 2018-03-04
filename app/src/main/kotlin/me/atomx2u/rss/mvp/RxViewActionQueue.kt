package me.atomx2u.rss.mvp

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.internal.functions.Functions
import java.util.*

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
