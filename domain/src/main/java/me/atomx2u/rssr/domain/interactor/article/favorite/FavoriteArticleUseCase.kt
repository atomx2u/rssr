package me.atomx2u.rssr.domain.interactor.article.favorite

import io.reactivex.Completable
import me.atomx2u.rssr.domain.Repository
import me.atomx2u.rssr.domain.arch.CompletableUseCase
import me.atomx2u.rssr.domain.arch.UcRequest

class FavoriteArticleUseCase(
    private val repo: Repository
) : CompletableUseCase<FavoriteArticleUseCase.Request> {

    override fun execute(request: Request): Completable {
        return repo.setFavoriteToArticle(request.articleId, true)
    }

    class Request(val articleId : Long) : UcRequest
}