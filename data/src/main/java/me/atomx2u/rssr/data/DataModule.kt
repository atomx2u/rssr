package me.atomx2u.rssr.data

import android.content.Context

class DataModule {
    companion object {
        fun install(context: Context) {
            com.raizlabs.android.dbflow.config.FlowManager.init(context)
        }
    }
}