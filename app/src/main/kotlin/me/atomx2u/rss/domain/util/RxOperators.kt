package me.atomx2u.rss.domain.util

import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import io.reactivex.exceptions.Exceptions
import io.reactivex.functions.Predicate
import io.reactivex.plugins.RxJavaPlugins

fun <T> Single<T>.applyIfTrue(predicate: Predicate<T>, applyBlock: () -> Unit): Single<T> {
    return RxJavaPlugins.onAssembly(lift({ downstreamObserver ->
        object : SingleObserver<T> {
            override fun onSubscribe(d: Disposable) {
                downstreamObserver.onSubscribe(d)
            }

            override fun onSuccess(t: T) {
                try {
                    if (predicate.test(t)) {
                        applyBlock()
                    }
                    downstreamObserver.onSuccess(t)
                } catch (e: Throwable) {
                    Exceptions.throwIfFatal(e)
                    onError(e)
                    return
                }
            }

            override fun onError(e: Throwable) {
                downstreamObserver.onError(e)
            }
        }
    }))
}