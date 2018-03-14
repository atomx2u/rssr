package me.atomx2u.rssr.dagger.app.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.atomx2u.rssr.ui.MainActivity
import me.atomx2u.rssr.dagger.ActivityScope
import me.atomx2u.rssr.dagger.main.MainActivityModule
import me.atomx2u.rssr.dagger.main.MainFragmentBindingsModule

@Module
interface ActivityBindingsModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [
        MainActivityModule::class,
        MainFragmentBindingsModule::class
    ])
    fun contributesMainActivityInjector(): MainActivity
}