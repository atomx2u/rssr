package me.atomx2u.rssr.dagger.main

import android.support.v7.app.AppCompatActivity
import com.jakewharton.rxrelay2.PublishRelay
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import me.atomx2u.rssr.ui.MainActivity
import me.atomx2u.rssr.dagger.ActivityScope
import me.atomx2u.rssr.domain.interactor.feed.FeedValidator
import me.atomx2u.rssr.domain.interactor.impl.FeedValidatorImpl
import me.atomx2u.rssr.mvp.BasePresenter
import me.atomx2u.rssr.mvp.MvpPresenter
import me.atomx2u.rssr.mvp.MvpView
import me.atomx2u.rssr.ui.MainActivityEvent
import me.atomx2u.rssr.ui.Navigator
import me.atomx2u.rssr.util.ActivityContext
import me.atomx2u.rssr.util.ImageLoader
import me.atomx2u.rssr.util.ImageLoaderImpl

@Module
class MainActivityModule {

    @Provides
    fun provideView(): MvpView = object : MvpView {}

    @Provides
    fun providePresenter(view: MvpView): MvpPresenter = object : BasePresenter<MvpView>(view) {}

    @ActivityScope
    @Provides
    fun provideNavigator(instance: MainActivity): Navigator = Navigator(instance)

    @ActivityScope
    @Provides
    fun provideEventRelay(): PublishRelay<MainActivityEvent> = PublishRelay.create()

    @Provides
    fun provideCompositeDisposable() = CompositeDisposable()

    @ActivityScope
    @Provides
    fun provideFeedValidator(): FeedValidator = FeedValidatorImpl()

    @ActivityScope
    @Provides
    fun provideImageLoader(activity: AppCompatActivity): ImageLoader = ImageLoaderImpl(ActivityContext(activity))
}