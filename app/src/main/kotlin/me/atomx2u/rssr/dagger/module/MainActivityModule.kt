package me.atomx2u.rssr.dagger.module

import dagger.Module
import dagger.Provides
import me.atomx2u.rssr.dagger.ActivityScope
import me.atomx2u.rssr.mvp.BasePresenter
import me.atomx2u.rssr.mvp.MvpPresenter
import me.atomx2u.rssr.mvp.MvpView

@Module
class MainActivityModule {

    @ActivityScope
    @Provides
    fun provideView(): MvpView = object : MvpView {}

    @ActivityScope
    @Provides
    fun providePresenter(view: MvpView): MvpPresenter = object: BasePresenter<MvpView>(view) {}
}