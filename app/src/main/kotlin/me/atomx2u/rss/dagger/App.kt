package me.atomx2u.rss.dagger

import android.app.Application
import com.raizlabs.android.dbflow.config.FlowManager

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        FlowManager.init(this)
    }
}