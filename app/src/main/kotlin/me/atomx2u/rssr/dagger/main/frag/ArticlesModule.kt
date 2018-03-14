package me.atomx2u.rssr.dagger.main.frag

import dagger.Binds
import dagger.Module
import me.atomx2u.rssr.dagger.FragmentScope
import me.atomx2u.rssr.ui.article.list.ArticlesContract
import me.atomx2u.rssr.ui.article.list.ArticlesPresenter
import me.atomx2u.rssr.ui.article.list.ViewController

@Module
interface ArticlesModule {
    @FragmentScope
    @Binds
    fun bindView(view: ViewController): ArticlesContract.View

    @FragmentScope
    @Binds
    fun bindPresenter(presenter: ArticlesPresenter): ArticlesContract.Presenter
}