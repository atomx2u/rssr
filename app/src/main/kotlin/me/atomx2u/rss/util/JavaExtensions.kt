package me.atomx2u.rss.util

import java.lang.ref.WeakReference

fun <T> WeakReference<T>.callIfNotNull(block: T.() -> Unit) = get()?.let(block)
