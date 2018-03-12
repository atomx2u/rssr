package me.atomx2u.rssr.data

import android.content.Context

class DataInitializer {
    companion object {
        fun init(context: Context) {
            com.raizlabs.android.dbflow.config.FlowManager.init(context)
        }
    }
}