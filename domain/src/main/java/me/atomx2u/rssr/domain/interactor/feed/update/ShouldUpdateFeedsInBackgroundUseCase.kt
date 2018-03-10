package me.atomx2u.rssr.domain.interactor.feed.update

import io.reactivex.Single
import me.atomx2u.rssr.domain.repository.Repository
import me.atomx2u.rssr.domain.arch.SingleUseCase
import me.atomx2u.rssr.domain.arch.UcRequest

class ShouldUpdateFeedsInBackgroundUseCase(
    private val repository: Repository
) : SingleUseCase<UcRequest.NONE, Boolean> {

    override fun execute(request: UcRequest.NONE): Single<Boolean> {
        return repository.shouldUpdateFeedsInBackground()
    }
}