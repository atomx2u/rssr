package me.atomx2u.rssr.data.util

class TimeUtilsImpl : TimeUtils {

    override fun getCurrentTime(): Long = System.currentTimeMillis()
}