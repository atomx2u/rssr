package me.atomx2u.rss.domain.interactor.update

import me.atomx2u.rss.domain.Repository
import me.atomx2u.rss.domain.arch.IRequest
import me.atomx2u.rss.domain.arch.SingleUseCaseWithRequest

//class UpdateFeedUseCase(
//    private val repository: Repository
//) : SingleUseCaseWithRequest<UpdateFeedUseCase.Request, Boolean> {
//
//    override fun execute(request: Request) = repository.updateFeed(request.feedId)
//
//    data class Request(
//        val feedId: Long
//    ) : IRequest
//}