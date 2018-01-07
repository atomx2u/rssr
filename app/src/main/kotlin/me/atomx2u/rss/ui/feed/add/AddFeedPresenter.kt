package me.atomx2u.rss.ui.feed.add

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.atomx2u.rss.base.BasePresenter
import me.atomx2u.rss.data.RepositoryImpl
import me.atomx2u.rss.data.database.DAOImpl
import me.atomx2u.rss.data.preference.Prefs
import me.atomx2u.rss.data.service.ServiceImpl
import me.atomx2u.rss.data.util.TimeUtils
import me.atomx2u.rss.domain.Repository
import me.atomx2u.rss.domain.component.FeedValidator
import me.atomx2u.rss.domain.interactor.feed.AddFeedUseCase
import me.atomx2u.rss.domain.interactor.feed.IsFeedSubscribedUseCase
import me.atomx2u.rss.ui.NavigationManager
import me.atomx2u.rss.util.callIfNotNull
import java.lang.ref.WeakReference

class AddFeedPresenter(
    view: AddFeedContract.View,
    private val navigator: NavigationManager,
    private val feedValidator: FeedValidator,
    prefs: Prefs, timeUtils: TimeUtils
) : BasePresenter<AddFeedContract.View>(view), AddFeedContract.Presenter {

    private val repo: Repository
    private val addFeedUseCase: AddFeedUseCase
    private val isFeedSubscribedUseCase: IsFeedSubscribedUseCase

    init {
        repo = RepositoryImpl(
            DAOImpl(), ServiceImpl(), prefs, timeUtils)
        addFeedUseCase = AddFeedUseCase(repo)
        isFeedSubscribedUseCase = IsFeedSubscribedUseCase(repo)
    }

    override fun create() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun resume() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun pause() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun destroy() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun back() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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

    private fun onAddNewFeedSubscriptionComplete() {
        view.callIfNotNull { switchLoading(false) }
        navigator.back()
        navigator.refreshUserSubscriptionFragment()
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
            is Exception -> {
                t.printStackTrace()
                view.callIfNotNull {
                    showErrorHint("Oops")
                }
            }
            else -> TODO()
        }
    }

    class FeedIsSubscribedException : Exception()
}

