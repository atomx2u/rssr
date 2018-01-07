package me.atomx2u.rss.domain.interactor.feed

import me.atomx2u.rss.domain.Feed
import me.atomx2u.rss.domain.Repository
import me.atomx2u.rss.domain.arch.SingleUseCase

class GetSubscribedFeedsUseCase(
    private val repo: Repository
) : SingleUseCase<List<Feed>> {

    override fun execute() = repo.getSubscribedFeeds()
}