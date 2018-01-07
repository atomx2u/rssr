package me.atomx2u.rss.device.util

import android.content.Context

class AppInfoUtilsImpl(
    private val context: Context
) : AppInfoUtils {

    override fun getAppAbsolutePath(): String {
        return context.filesDir.toString()
    }
}