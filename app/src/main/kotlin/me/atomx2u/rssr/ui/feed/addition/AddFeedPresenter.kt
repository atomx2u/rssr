package me.atomx2u.rssr.ui.feed.addition

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.atomx2u.rssr.dagger.App
import me.atomx2u.rssr.mvp.BasePresenter
import me.atomx2u.rssr.data.pref.Prefs
import me.atomx2u.rssr.data.util.TimeUtils
import me.atomx2u.rssr.domain.repository.Repository
import me.atomx2u.rssr.domain.interactor.feed.FeedValidator
import me.atomx2u.rssr.domain.interactor.feed.AddFeedUseCase
import me.atomx2u.rssr.domain.interactor.feed.IsFeedSubscribedUseCase
import me.atomx2u.rssr.ui.Navigator
import java.lang.ref.WeakReference

class AddFeedPresenter(
    view: AddFeedContract.View,
    private val navigator: Navigator,
    private val feedValidator: FeedValidator,
    prefs: Prefs,
    timeUtils: TimeUtils
) : BasePresenter<AddFeedContract.View>(view), AddFeedContract.Presenter {

    var onNewFeedAdded: WeakReference<OnNewFeedAdded>? = null

    private val repo: Repository? = null
    private val addFeedUseCase: AddFeedUseCase
    private val isFeedSubscribedUseCase: IsFeedSubscribedUseCase

    init {
//        repo =
        addFeedUseCase = AddFeedUseCase(repo!!)
        isFeedSubscribedUseCase = IsFeedSubscribedUseCase(repo!!)
    }

    override fun addNewFeedSubscription(feedLink: String) {
        view.get()?.clearErrorHint()
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
        onNewFeedAdded?.get()?.onNewFeedAdded()
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

    interface OnNewFeedAdded {
        fun onNewFeedAdded()
    }
}

