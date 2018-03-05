package me.atomx2u.rssr.domain.interactor.update

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