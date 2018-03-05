package me.atomx2u.rssr.domain.interactor.feed

import me.atomx2u.rssr.domain.Feed
import me.atomx2u.rssr.domain.Repository
import me.atomx2u.rssr.domain.arch.SingleUseCase

class GetSubscribedFeedsUseCase(
    private val repo: Repository
) : SingleUseCase<List<Feed>> {

    override fun execute() = repo.getSubscribedFeeds()
}