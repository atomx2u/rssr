package me.atomx2u.rssr.ui.article.list

import android.util.Log
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import me.atomx2u.rssr.domain.interactor.article.GetArticlesUseCase
import me.atomx2u.rssr.domain.interactor.article.favorite.FavoriteArticleUseCase
import me.atomx2u.rssr.domain.interactor.article.favorite.UnFavoriteArticleUseCase
import me.atomx2u.rssr.domain.model.Article
import me.atomx2u.rssr.mvp.BasePresenter
import me.atomx2u.rssr.ui.Navigator
import javax.inject.Inject

class ArticlesPresenter @Inject constructor(
    view: ArticlesContract.View,
    private val navigator: Navigator
) : BasePresenter<ArticlesContract.View>(view), ArticlesContract.Presenter {


    @Inject
    lateinit var getArticlesUseCase: GetArticlesUseCase
    @Inject
    lateinit var favoriteArticleUseCase: FavoriteArticleUseCase
    @Inject
    lateinit var unFavoriteArticleUseCase: UnFavoriteArticleUseCase

    override fun back() {
        navigator.back()
    }

    override fun showArticles(feedId: Long) {
        viewActionQueue.subscribeTo(
            getArticlesUseCase.execute(GetArticlesUseCase.Request(feedId)),
            onSuccess = Consumer { articles -> view.get()?.showArticles(articles) }
        )
    }

    override fun showArticleContent(article: Article) {
        navigator.showArticleDetail(article.link)
    }

    override fun toggleArticleFavorite(article: Article) {
        viewActionQueue.subscribeTo(
            if (article.isFavorite) unFavoriteArticleUseCase.execute(UnFavoriteArticleUseCase.Request(article.id))
            else favoriteArticleUseCase.execute(FavoriteArticleUseCase.Request(article.id)),
            onComplete = Action {
                Log.i("yes", "yes")
            },
            onError = Consumer {
                Log.i("yes", "no")
            } //TODO 取消toggle
        )
    }
}