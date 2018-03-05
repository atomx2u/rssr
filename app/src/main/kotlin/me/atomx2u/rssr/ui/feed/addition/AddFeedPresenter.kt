package me.atomx2u.rssr.ui.feed.addition

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.atomx2u.rssr.mvp.BasePresenter
import me.atomx2u.rssr.data.RepositoryImpl
import me.atomx2u.rssr.data.database.DAOImpl
import me.atomx2u.rssr.data.preference.Prefs
import me.atomx2u.rssr.data.service.ServiceImpl
import me.atomx2u.rssr.data.util.TimeUtils
import me.atomx2u.rssr.domain.Repository
import me.atomx2u.rssr.domain.component.FeedValidator
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

    private val repo: Repository
    private val addFeedUseCase: AddFeedUseCase
    private val isFeedSubscribedUseCase: IsFeedSubscribedUseCase

    init {
        repo = RepositoryImpl(
            DAOImpl(), ServiceImpl(), prefs, timeUtils)
        addFeedUseCase = AddFeedUseCase(repo)
        isFeedSubscribedUseCase = IsFeedSubscribedUseCase(repo)
    }

    override fun addNewFeedSubscription(feedLink: String) {
        view.callIfNotNull { clearErrorHint() }
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
        navigator.back()
    }

    private fun onAddNewFeedSubscriptionComplete() {
        view.callIfNotNull { switchLoading(false) }
        navigator.back()
        onNewFeedAdded?.callIfNotNull { onNewFeedAdded() }
    }

    private fun onAddNewFeedSubscriptionError(t: Throwable?) {
        view.callIfNotNull { switchLoading(false) }
        when (t) {
            is FeedValidator.InvalidFeedLinkException -> {
                view.callIfNotNull { showErrorHint("Invalid Feed Link.") }
            }
            is FeedIsSubscribedException -> {
                view.callIfNotNull { showErrorHint("The Feed is subscribed.") }
            }
            else -> {
                (t as? Exception)?.printStackTrace()
                view.callIfNotNull {
                    showErrorHint("Oops")
                }
            }
        }
    }

    class FeedIsSubscribedException : Exception()

    interface OnNewFeedAdded {
        fun onNewFeedAdded()
    }
}

