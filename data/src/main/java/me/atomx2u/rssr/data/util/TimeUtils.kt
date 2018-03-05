package me.atomx2u.rssr.data.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

interface TimeUtils {
    fun getCurrentTime(): Long
    fun getCurrentDay(
        dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.SIMPLIFIED_CHINESE)
    ): String = dateFormat.format(Date(getCurrentTime()))

    fun getCurrentSeconds(
        dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE)
    ): String = dateFormat.format(Date(getCurrentTime()))

    fun getCurrentMillis(
        dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.SIMPLIFIED_CHINESE)
    ): String = dateFormat.format(Date(getCurrentTime()))
}