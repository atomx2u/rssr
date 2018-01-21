package me.atomx2u.rss.domain.interactor.feed

import me.atomx2u.rss.domain.Repository
import me.atomx2u.rss.domain.arch.CompletableUseCaseWithRequest
import me.atomx2u.rss.domain.arch.IRequest
import me.atomx2u.rss.domain.arch.SingleUseCaseWithRequest

/**
 * delete a existed feed source. take care with non-subscribed feed.
 * @see IsFeedSubscribedUseCase
 * */
class DeleteFeedUseCase(
    private val repo: Repository
) : CompletableUseCaseWithRequest<DeleteFeedUseCase.Request> {

    override fun execute(request: Request) = repo.deleteFeed(request.feedId)

    data class Request(
        val feedId: Long
    ) : IRequest
}
