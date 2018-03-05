package me.atomx2u.rssr.ui.article.list

import io.reactivex.functions.Consumer
import me.atomx2u.rssr.domain.Article
import me.atomx2u.rssr.domain.Repository
import me.atomx2u.rssr.domain.interactor.article.GetArticlesUseCase
import me.atomx2u.rssr.mvp.BasePresenter
import me.atomx2u.rssr.ui.Navigator

class ArticlesPresenter(
    view: ArticlesContract.View,
    private val navigator: Navigator,
    repo: Repository
) : BasePresenter<ArticlesContract.View>(view), ArticlesContract.Presenter {

    var feedId: Long? = null

    private val getArticlesUseCase = GetArticlesUseCase(repo)

    override fun back() {
        navigator.back()
    }

    override fun showArticles(feedId: Long) {
        this.feedId = feedId
        viewActionQueue.subscribeTo(
            getArticlesUseCase.execute(GetArticlesUseCase.Request(feedId)),
            onSuccess = Consumer { articles -> view.get()?.showArticles(articles) }
        )
    }

    override fun showArticleContent(article: Article) {
        navigator.showArticleDetail(article.link)
    }
}