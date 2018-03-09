package me.atomx2u.rssr.domain.interactor.article.favorite

import io.reactivex.Completable
import me.atomx2u.rssr.domain.Repository
import me.atomx2u.rssr.domain.arch.CompletableUseCase
import me.atomx2u.rssr.domain.arch.UcRequest

class UnFavoriteArticleUseCase(
    private val repository: Repository
) : CompletableUseCase<UnFavoriteArticleUseCase.Request> {

    override fun execute(request: UnFavoriteArticleUseCase.Request): Completable {
        return repository.setFavoriteToArticle(request.articleId, false)
    }

    class Request(val articleId: Long) : UcRequest
}