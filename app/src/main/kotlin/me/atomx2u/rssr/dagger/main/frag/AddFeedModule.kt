package me.atomx2u.rssr.dagger.main.frag

import dagger.Binds
import dagger.Module
import me.atomx2u.rssr.dagger.FragmentScope
import me.atomx2u.rssr.ui.feed.addition.AddFeedContract
import me.atomx2u.rssr.ui.feed.addition.AddFeedPresenter
import me.atomx2u.rssr.ui.feed.addition.ViewController

@Module
interface AddFeedModule {
    @FragmentScope
    @Binds
    fun bindView(view: ViewController): AddFeedContract.View

    @FragmentScope
    @Binds
    fun bindPresenter(presenter: AddFeedPresenter): AddFeedContract.Presenter
}