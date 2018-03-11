package me.atomx2u.rssr.dagger

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.atomx2u.rssr.MainActivity

@Module
interface AModule {

    @ActivityScope
    @ContributesAndroidInjector
    fun contributesMainActivityInjector(): MainActivity
}