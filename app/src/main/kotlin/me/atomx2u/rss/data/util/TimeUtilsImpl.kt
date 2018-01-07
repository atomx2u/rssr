package me.atomx2u.rss.data.util

class TimeUtilsImpl : TimeUtils {

    override fun getCurrentTime(): Long = System.currentTimeMillis()
}