package me.atomx2u.rssr.dagger.app

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import me.atomx2u.rssr.data.DataInitializer

class App : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        DataInitializer.init(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.builder()
            .create(this)
}