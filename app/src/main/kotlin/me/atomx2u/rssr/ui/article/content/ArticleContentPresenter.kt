package me.atomx2u.rssr.ui.article.content

import me.atomx2u.rssr.mvp.BasePresenter
import me.atomx2u.rssr.mvp.MvpView
import me.atomx2u.rssr.ui.Navigator

class ArticleContentPresenter(
    view: MvpView,
    private val navigator: Navigator
) : BasePresenter<MvpView>(view) {

    override fun back() {
        navigator.back()
    }
}