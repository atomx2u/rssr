package me.atomx2u.rssr.domain.interactor.article

import me.atomx2u.rssr.domain.Repository
import me.atomx2u.rssr.domain.arch.CompletableUseCaseWithRequest
import me.atomx2u.rssr.domain.arch.IRequest

class MarkArticleAsReadUseCase(
    private val repo: Repository
) : CompletableUseCaseWithRequest<MarkArticleAsReadUseCase.Request> {

    override fun execute(request: Request) =
        repo.markArticleAsRead(request.articleId)

    data class Request(
        val articleId: Long
    ) : IRequest
}