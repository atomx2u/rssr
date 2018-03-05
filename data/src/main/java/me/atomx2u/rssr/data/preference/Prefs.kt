package me.atomx2u.rssr.data.preference

import android.content.Context
import android.content.Context.MODE_PRIVATE

class Prefs(context: Context) {

    private val prefs = context.getSharedPreferences(FILE_NAME, MODE_PRIVATE)

    fun getIsAutoFeedsUpdateEnabled() =
        prefs.getBoolean(IS_AUTO_FEEDS_UPDATE_ENABLED, false)

    fun setIsAutoFeedsUpdateEnabled(value: Boolean) =
        prefs.edit().putBoolean(IS_AUTO_FEEDS_UPDATE_ENABLED, value).commit()

    companion object {
        private const val FILE_NAME = "me.atomx2u.rss"
        private const val IS_AUTO_FEEDS_UPDATE_ENABLED = "isAutoFeedsUpdateEnabled"
    }
}