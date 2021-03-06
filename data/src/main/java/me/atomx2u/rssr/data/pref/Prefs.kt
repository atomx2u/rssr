package me.atomx2u.rssr.data.pref

import android.content.Context
import android.content.Context.MODE_PRIVATE

class Prefs(context: Context) {

    private val prefs = context.getSharedPreferences(FILE_NAME, MODE_PRIVATE)

    fun shouldUpdateFeedsInBackground() =
        prefs.getBoolean(IS_AUTO_FEEDS_UPDATE_ENABLED, false)

    fun setShouldUpdateFeedsInBackground(value: Boolean) =
        prefs.edit().putBoolean(IS_AUTO_FEEDS_UPDATE_ENABLED, value).commit()

    companion object {
        private const val FILE_NAME = "me.atomx2u.rss"
        private const val IS_AUTO_FEEDS_UPDATE_ENABLED = "isAutoFeedsUpdateEnabled"
    }
}