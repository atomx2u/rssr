package me.atomx2u.rssr.domain.interactor.feed

import io.reactivex.Completable
import me.atomx2u.rssr.domain.repository.Repository
import me.atomx2u.rssr.domain.arch.CompletableUseCase
import me.atomx2u.rssr.domain.arch.UcRequest

/**
 * add a new feed source.watch out input scope.
 * @see IsFeedSubscribedUseCase
 * @see FeedValidator
 * */
class AddFeedUseCase (
    private val repo: Repository
) : CompletableUseCase<AddFeedUseCase.Request> {

    override fun execute(request: Request): Completable = repo.insertFeed(request.feedLink)

    data class Request(val feedLink: String) : UcRequest
}

