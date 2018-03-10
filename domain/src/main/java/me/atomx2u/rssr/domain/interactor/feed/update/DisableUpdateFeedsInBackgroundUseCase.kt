package me.atomx2u.rssr.domain.interactor.feed.update

import io.reactivex.Completable
import me.atomx2u.rssr.domain.repository.Repository
import me.atomx2u.rssr.domain.arch.CompletableUseCase
import me.atomx2u.rssr.domain.arch.UcRequest

class DisableUpdateFeedsInBackgroundUseCase(
    private val repository: Repository
): CompletableUseCase<UcRequest.NONE> {

    override fun execute(request: UcRequest.NONE): Completable {
        return repository.setShouldUpdateFeedsInBackground(false)
    }
}