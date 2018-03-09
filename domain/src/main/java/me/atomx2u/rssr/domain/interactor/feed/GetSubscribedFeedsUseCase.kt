package me.atomx2u.rssr.domain.interactor.feed

import me.atomx2u.rssr.domain.model.Feed
import me.atomx2u.rssr.domain.Repository
import me.atomx2u.rssr.domain.arch.SingleUseCase
import me.atomx2u.rssr.domain.arch.UcRequest

class GetSubscribedFeedsUseCase(
    private val repo: Repository
) : SingleUseCase<UcRequest.NONE, List<Feed>> {

    override fun execute(request: UcRequest.NONE) = repo.getSubscribedFeeds()
}