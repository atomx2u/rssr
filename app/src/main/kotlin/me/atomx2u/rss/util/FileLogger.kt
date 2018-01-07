package me.atomx2u.rss.util

interface FileLogger {
    fun v(msg: String)
    fun w(msg: String)
    fun e(msg: String)
}