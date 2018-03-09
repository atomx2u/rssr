package me.atomx2u.rssr.domain.interactor.update

import me.atomx2u.rssr.domain.Repository
import me.atomx2u.rssr.domain.arch.SingleUseCase
import me.atomx2u.rssr.domain.arch.UcRequest

class IsAutoFeedsUpdateEnableUseCase(
    private val repository: Repository
): SingleUseCase<UcRequest.NONE, Boolean> {

    override fun execute(request: UcRequest.NONE) = repository.isAutoFeedsUpdateEnabled()
}