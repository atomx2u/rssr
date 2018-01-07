package me.atomx2u.rss.domain.interactor.article

import io.reactivex.Single
import me.atomx2u.rss.domain.Repository
import me.atomx2u.rss.domain.arch.CompletableUseCaseWithRequest
import me.atomx2u.rss.domain.arch.IRequest
import me.atomx2u.rss.domain.arch.SingleUseCaseWithRequest

class MarkArticleAsReadUseCase(
    private val repo: Repository
) : CompletableUseCaseWithRequest<MarkArticleAsReadUseCase.Request> {

    override fun execute(request: Request) =
        repo.markArticleAsRead(request.articleId)

    data class Request(
        val articleId: Long
    ) : IRequest
}