package me.atomx2u.rssr.domain.interactor.feed.update

import io.reactivex.Completable
import me.atomx2u.rssr.domain.repository.Repository
import me.atomx2u.rssr.domain.arch.CompletableUseCase
import me.atomx2u.rssr.domain.arch.UcRequest
import me.atomx2u.rssr.domain.model.Feed

class UpdateFeedUseCase(
    private val repository: Repository
): CompletableUseCase<UpdateFeedUseCase.Request>{

    override fun execute(request: Request): Completable {
        return repository.pullArticlesForFeedFromOrigin(request.feed)
    }

    data class Request(val feed: Feed) : UcRequest
}