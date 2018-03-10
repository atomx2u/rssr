package me.atomx2u.rssr.ui.feed.subscription

import io.reactivex.android.schedulers.AndroidSchedulers
import me.atomx2u.rssr.domain.model.Feed
import me.atomx2u.rssr.domain.repository.Repository
import me.atomx2u.rssr.domain.arch.UcRequest
import me.atomx2u.rssr.domain.interactor.feed.DeleteFeedUseCase
import me.atomx2u.rssr.domain.interactor.feed.GetSubscribedFeedsUseCase
import me.atomx2u.rssr.mvp.BasePresenter
import me.atomx2u.rssr.ui.Navigator
import me.atomx2u.rssr.ui.model.FeedViewModel
import me.atomx2u.rssr.ui.model.toViewModel

class UserSubscriptionPresenter(
    view: UserSubscriptionContract.View,
    repo: Repository,
    private val navigator: Navigator
) : BasePresenter<UserSubscriptionContract.View>(view), UserSubscriptionContract.Presenter {

    private val getSubscribedFeedsUseCase = GetSubscribedFeedsUseCase(repo)
    private val deleteFeedUseCase = DeleteFeedUseCase(repo)

    override fun back() {
        navigator.back()
    }

    override fun updateFavoriteFeedSubscriptions() {
        getSubscribedFeedsUseCase.execute(UcRequest.NONE)
            .map { it.map(Feed::toViewModel) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::onUpdateFeedSubscriptionsSuccess)
    }

    override fun unsubscribeFeed(feed: FeedViewModel) {
        deleteFeedUseCase.execute(DeleteFeedUseCase.Request(feed.id))
            .subscribe(::updateFavoriteFeedSubscriptions)
    }

    private fun onUpdateFeedSubscriptionsSuccess(feeds: List<FeedViewModel>) {
        view.get()?.showFeedSubscriptions(feeds)
    }

    override fun showArticles(feed: FeedViewModel) {
        navigator.showArticles(feed.id)
    }

    override fun showAddNewFeed() {
        navigator.showAddFeed()
    }
}