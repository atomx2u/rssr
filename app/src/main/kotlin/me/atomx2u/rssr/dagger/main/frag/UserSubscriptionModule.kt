package me.atomx2u.rssr.dagger.main.frag

import dagger.Binds
import dagger.Module
import me.atomx2u.rssr.dagger.FragmentScope
import me.atomx2u.rssr.ui.feed.subscription.UserSubscriptionContract
import me.atomx2u.rssr.ui.feed.subscription.UserSubscriptionPresenter
import me.atomx2u.rssr.ui.feed.subscription.ViewController

@Module
interface UserSubscriptionModule {

    @FragmentScope
    @Binds
    abstract fun bindView(view: ViewController): UserSubscriptionContract.View

    @FragmentScope
    @Binds
    abstract fun bindPresenter(presenter: UserSubscriptionPresenter): UserSubscriptionContract.Presenter
}