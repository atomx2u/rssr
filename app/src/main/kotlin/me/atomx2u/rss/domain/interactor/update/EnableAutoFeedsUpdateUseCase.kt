package me.atomx2u.rss.domain.interactor.update

import io.reactivex.Single
import io.reactivex.functions.Predicate
import me.atomx2u.rss.domain.Repository
import me.atomx2u.rss.domain.arch.SingleUseCase
import me.atomx2u.rss.domain.component.FeedsUpdateScheduler
import me.atomx2u.rss.domain.util.applyIfTrue

//class EnableAutoFeedsUpdateUseCase(
//    private val repository: Repository,
//    private val feedsUpdateScheduler: FeedsUpdateScheduler
//) : SingleUseCase<Boolean> {
//
//    override fun execute(): Single<Boolean> = repository.setAutoFeedsUpdate(true)
//        .applyIfTrue(Predicate { result: Boolean -> result }) {
//            feedsUpdateScheduler.enableFeedsUpdate()
//        }
//}