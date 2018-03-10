package me.atomx2u.rssr.domain.interactor.feed

import me.atomx2u.rssr.domain.repository.Repository
import me.atomx2u.rssr.domain.arch.SingleUseCase
import me.atomx2u.rssr.domain.arch.UcRequest

/**
 * judge whether a feed subscribed or not.
 * @see AddFeedUseCase
 * @see DeleteFeedUseCase
 * */
class IsFeedSubscribedUseCase(
    private val repo: Repository
) : SingleUseCase<IsFeedSubscribedUseCase.Request, Boolean> {

    override fun execute(request: Request) = repo.doesFeedExists(request.link)

    data class Request(val link: String) : UcRequest
}