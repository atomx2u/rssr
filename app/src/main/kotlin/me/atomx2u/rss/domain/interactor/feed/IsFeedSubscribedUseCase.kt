package me.atomx2u.rss.domain.interactor.feed

import me.atomx2u.rss.domain.Repository
import me.atomx2u.rss.domain.arch.IRequest
import me.atomx2u.rss.domain.arch.SingleUseCaseWithRequest

/**
 * judge whether a feed subscribed or not.
 * @see AddFeedUseCase
 * @see DeleteFeedUseCase
 * */
class IsFeedSubscribedUseCase(
    private val repo: Repository
) : SingleUseCaseWithRequest<IsFeedSubscribedUseCase.Request, Boolean> {

    override fun execute(request: Request) = repo.doesFeedExists(request.link)

    data class Request(
        val link: String
    ) : IRequest
}