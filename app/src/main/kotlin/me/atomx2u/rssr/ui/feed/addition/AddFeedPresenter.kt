package me.atomx2u.rssr.ui.feed.addition

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.atomx2u.rssr.domain.interactor.feed.AddFeedUseCase
import me.atomx2u.rssr.domain.interactor.feed.FeedValidator
import me.atomx2u.rssr.domain.interactor.feed.IsFeedSubscribedUseCase
import me.atomx2u.rssr.mvp.BasePresenter
import me.atomx2u.rssr.ui.MainActivityEvent
import me.atomx2u.rssr.ui.NewFeedAdded
import javax.inject.Inject

class AddFeedPresenter @Inject constructor(
    view: AddFeedContract.View,
    private val feedValidator: FeedValidator
) : BasePresenter<AddFeedContract.View>(view), AddFeedContract.Presenter {

    @Inject
    lateinit var eventBus: PublishRelay<MainActivityEvent>
    @Inject
    lateinit var addFeedUseCase: AddFeedUseCase
    @Inject
    lateinit var isFeedSubscribedUseCase: IsFeedSubscribedUseCase

    override fun addNewFeedSubscription(feedLink: String) {
        view.get()?.clearErrorHint()
        view.get()?.switchLoading(true)
        feedValidator.validateFeed(feedLink)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .andThen(isFeedSubscribedUseCase.execute(IsFeedSubscribedUseCase.Request(feedLink)))
            .map { isFeedSubscribed -> if (isFeedSubscribed) throw FeedIsSubscribedException() }
            .toCompletable()
            .andThen(addFeedUseCase.execute(AddFeedUseCase.Request(feedLink)))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::onAddNewFeedSubscriptionComplete,
                this::onAddNewFeedSubscriptionError)
    }

    override fun back() {
        view.get()?.dismiss()
    }

    private fun onAddNewFeedSubscriptionComplete() {
        view.get()?.switchLoading(false)
        eventBus.accept(NewFeedAdded)
        back()
    }

    private fun onAddNewFeedSubscriptionError(t: Throwable?) {
        view.get()?.switchLoading(false)
        when (t) {
            is FeedValidator.InvalidFeedLinkException -> {
                view.get()?.showErrorHint("Invalid Feed Link.")
            }
            is FeedIsSubscribedException -> {
                view.get()?.showErrorHint("The Feed is subscribed.")
            }
            else -> {
                (t as? Exception)?.printStackTrace()
                view.get()?.showErrorHint("Oops")
            }
        }
    }

    class FeedIsSubscribedException : Exception()
}

