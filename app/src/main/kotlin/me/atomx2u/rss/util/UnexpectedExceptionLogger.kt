package me.atomx2u.rss.util

import android.support.annotation.WorkerThread

class UnexpectedExceptionLogger(
    private val fileLogger: FileLogger
) {

    @WorkerThread
    fun assert(result: Boolean, expected: Boolean, extraFailMsg: String): Boolean {
        if (result != expected) {
            log(extraFailMsg)
        }
        return result
    }

    fun assert(result: Boolean, expected: Boolean, extraFailMsg: () -> String) =
        assert(result, expected, extraFailMsg())

    @WorkerThread
    fun log(extraFailMsg: String) {
        fileLogger.w("${Thread.currentThread().stackTrace}\nExtra Message:\n$extraFailMsg")
    }

    @WorkerThread
    fun log(extraFailMsg: () -> String) = log(extraFailMsg())
}

//private fun <T> Single<T>.logError(): Single<T> {
//    return doOnError { throwable ->
//        unexpectedExceptionLogger.log(throwable.toString())
//    }
//}