package me.atomx2u.rssr.ui.article.list

import me.atomx2u.rssr.domain.model.Article
import me.atomx2u.rssr.mvp.MvpPresenter
import me.atomx2u.rssr.mvp.MvpView

interface ArticlesContract {

    interface View : MvpView {
        fun showArticles(articles: List<Article>)
    }

    interface Presenter : MvpPresenter {
        fun showArticles(feedId: Long)
        fun showArticleContent(article: Article)
        fun toggleArticleFavorite(article: Article)
    }
}