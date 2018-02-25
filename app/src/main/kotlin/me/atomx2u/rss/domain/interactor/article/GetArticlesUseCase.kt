package me.atomx2u.rss.domain.interactor.article

import me.atomx2u.rss.domain.Article
import me.atomx2u.rss.domain.Repository
import me.atomx2u.rss.domain.arch.IRequest
import me.atomx2u.rss.domain.arch.SingleUseCaseWithRequest

class GetArticlesUseCase(
    private val repo: Repository
) : SingleUseCaseWithRequest<GetArticlesUseCase.Request, List<Article>> {

    override fun execute(request: Request) = repo.getArticles(request.feedId)

    data class Request(
        val feedId: Long
    ) : IRequest
}