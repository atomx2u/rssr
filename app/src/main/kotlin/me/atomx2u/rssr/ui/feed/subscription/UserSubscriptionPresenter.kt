package me.atomx2u.rssr.ui.feed.subscription

import me.atomx2u.rssr.domain.Feed
import me.atomx2u.rssr.domain.Repository
import me.atomx2u.rssr.mvp.BasePresenter
import me.atomx2u.rssr.ui.Navigator

class UserSubscriptionPresenter(
    view: UserSubscriptionContract.View,
    private val repo: Repository,
    private val navigator: Navigator
) : BasePresenter<UserSubscriptionContract.View>(view), UserSubscriptionContract.Presenter {

    override fun back() {
        navigator.back()
    }

    override fun updateFeedSubscriptions() {
        repo.getSubscribedFeeds()
            .subscribe(this::onUpdateFeedSubscriptionsSuccess)
    }

    private fun onUpdateFeedSubscriptionsSuccess(feeds: List<Feed>) {
        view.callIfNotNull { showFeedSubscriptions(feeds) }
    }

    override fun showArticles(feed: Feed) {
        navigator.showArticles(feed.id)
    }

    override fun showAddNewFeed() {
        navigator.showAddFeed()
    }
}