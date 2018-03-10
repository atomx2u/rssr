package me.atomx2u.rssr.domain.interactor.article.favorite

import io.reactivex.Single
import me.atomx2u.rssr.domain.repository.Repository
import me.atomx2u.rssr.domain.arch.SingleUseCase
import me.atomx2u.rssr.domain.arch.UcRequest
import me.atomx2u.rssr.domain.model.Article

class GetFavoriteArticlesUseCase(
    private val repository: Repository
) : SingleUseCase<UcRequest.NONE, List<Article>> {

    override fun execute(request: UcRequest.NONE): Single<List<Article>> {
        return repository.getFavoriteArticles()
    }
}