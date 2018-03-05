package me.atomx2u.rssr.domain.interactor.feed

import io.reactivex.Completable
import me.atomx2u.rssr.domain.Repository
import me.atomx2u.rssr.domain.arch.IRequest
import me.atomx2u.rssr.domain.arch.SolvedCompletableUseCaseWithRequest
import me.atomx2u.rssr.domain.component.FeedValidator

/**
 * add a new feed source.watch out input scope.
 * @see IsFeedSubscribedUseCase
 * @see FeedValidator
 * */
class AddFeedUseCase(
    private val repo: Repository
) : SolvedCompletableUseCaseWithRequest<AddFeedUseCase.Request> {

    override fun execute(request: Request): Completable =
        repo.insertFeed(request.feedLink)

    data class Request(
        val feedLink: String
    ) : IRequest
}

