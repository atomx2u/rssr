package me.atomx2u.rssr.domain.interactor.update

//class DisableAutoFeedsUpdateUseCase(
//    private val repository: Repository,
//    private val feedsUpdateScheduler: FeedsUpdateScheduler
//) : SingleUseCase<Boolean> {
//
//    override fun execute(): Single<Boolean> = repository.setAutoFeedsUpdate(false)
//        .applyIfTrue(Predicate { result: Boolean -> result }) {
//            feedsUpdateScheduler.disableFeedsUpdate()
//        }
//}