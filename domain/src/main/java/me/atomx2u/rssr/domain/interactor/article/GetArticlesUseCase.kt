package me.atomx2u.rssr.domain.interactor.article

import me.atomx2u.rssr.domain.model.Article
import me.atomx2u.rssr.domain.repository.Repository
import me.atomx2u.rssr.domain.arch.UcRequest
import me.atomx2u.rssr.domain.arch.SingleUseCase

class GetArticlesUseCase(
    private val repo: Repository
) : SingleUseCase<GetArticlesUseCase.Request, List<Article>> {

    override fun execute(request: Request) = repo.getArticles(request.feedId)

    data class Request(val feedId: Long) : UcRequest
}