package me.atomx2u.rssr.domain.interactor.feed

import me.atomx2u.rssr.domain.repository.Repository
import me.atomx2u.rssr.domain.arch.CompletableUseCase
import me.atomx2u.rssr.domain.arch.UcRequest

/**
 * delete a existed feed source. take care with non-subscribed feed.
 * @see IsFeedSubscribedUseCase
 * */
class DeleteFeedUseCase(
    private val repo: Repository
) : CompletableUseCase<DeleteFeedUseCase.Request> {

    override fun execute(request: Request) = repo.deleteFeed(request.feedId)

    data class Request(val feedId: Long) : UcRequest
}

