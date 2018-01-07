package me.atomx2u.rss.domain.interactor.update

import me.atomx2u.rss.domain.Repository
import me.atomx2u.rss.domain.arch.SingleUseCase

class IsAutoFeedsUpdateEnableUseCase(
    private val repository: Repository
): SingleUseCase<Boolean> {

    override fun execute() = repository.isAutoFeedsUpdateEnabled()
}