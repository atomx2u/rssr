package me.atomx2u.rssr.dagger.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.atomx2u.rssr.MainActivity
import me.atomx2u.rssr.dagger.ActivityScope

@Module
interface MainActivityInstallModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    fun contributesMainActivityInjector(): MainActivity
}