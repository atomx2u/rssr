package me.atomx2u.rssr.domain.interactor.update

import me.atomx2u.rssr.domain.Repository
import me.atomx2u.rssr.domain.arch.SingleUseCase

class IsAutoFeedsUpdateEnableUseCase(
    private val repository: Repository
): SingleUseCase<Boolean> {

    override fun execute() = repository.isAutoFeedsUpdateEnabled()
}