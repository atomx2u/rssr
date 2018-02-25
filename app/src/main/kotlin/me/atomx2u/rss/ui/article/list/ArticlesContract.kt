package me.atomx2u.rss.ui.article.list

import me.atomx2u.rss.domain.Article
import me.atomx2u.rss.mvp.MvpPresenter
import me.atomx2u.rss.mvp.MvpView

interface ArticlesContract {

    interface View : MvpView {
        fun showArticles(articles: List<Article>)
    }

    interface Presenter : MvpPresenter {
        fun showArticles(feedId: Long)
        fun showArticleContent(article: Article)
    }
}