package me.atomx2u.rssr.domain.interactor.article

import me.atomx2u.rssr.domain.Repository
import me.atomx2u.rssr.domain.arch.CompletableUseCase
import me.atomx2u.rssr.domain.arch.UcRequest

class MarkArticleAsReadUseCase(
    private val repo: Repository
) : CompletableUseCase<MarkArticleAsReadUseCase.Request> {

    override fun execute(request: Request) =
        repo.markArticleAsRead(request.articleId)

    data class Request(val articleId: Long) : UcRequest
}