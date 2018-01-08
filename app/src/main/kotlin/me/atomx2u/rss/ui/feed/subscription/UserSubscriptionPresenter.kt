package me.atomx2u.rss.ui.feed.subscription

import me.atomx2u.rss.base.BasePresenter
import me.atomx2u.rss.data.RepositoryImpl
import me.atomx2u.rss.domain.Feed
import me.atomx2u.rss.domain.Repository
import me.atomx2u.rss.ui.NavigationManager
import me.atomx2u.rss.util.callIfNotNull
import java.lang.ref.WeakReference

class UserSubscriptionPresenter(
    view: UserSubscriptionContract.View,
    private val repo: Repository,
    private val navigator: NavigationManager
) : BasePresenter<UserSubscriptionContract.View>(view), UserSubscriptionContract.Presenter {

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
        navigator.showAddFeedFragment()
    }
}