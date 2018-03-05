package me.atomx2u.rssr.domain.interactor.article

import me.atomx2u.rssr.domain.Article
import me.atomx2u.rssr.domain.Repository
import me.atomx2u.rssr.domain.arch.IRequest
import me.atomx2u.rssr.domain.arch.SingleUseCaseWithRequest

class GetArticlesUseCase(
    private val repo: Repository
) : SingleUseCaseWithRequest<GetArticlesUseCase.Request, List<Article>> {

    override fun execute(request: Request) = repo.getArticles(request.feedId)

    data class Request(
        val feedId: Long
    ) : IRequest
}