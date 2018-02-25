package me.atomx2u.rss.ui.article.content

import me.atomx2u.rss.mvp.BasePresenter
import me.atomx2u.rss.mvp.MvpView
import me.atomx2u.rss.ui.Navigator

class ArticleContentPresenter(
    view: MvpView,
    private val navigator: Navigator
) : BasePresenter<MvpView>(view) {

    override fun back() {
        navigator.back()
    }
}