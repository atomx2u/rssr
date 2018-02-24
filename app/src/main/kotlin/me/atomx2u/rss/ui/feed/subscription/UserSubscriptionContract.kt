package me.atomx2u.rss.ui.feed.subscription

import me.atomx2u.rss.mvp.MvpPresenter
import me.atomx2u.rss.domain.Feed
import me.atomx2u.rss.mvp.MvpView

interface UserSubscriptionContract {

    interface View : MvpView {
        fun showFeedSubscriptions(feeds: List<Feed>)
    }

    interface Presenter : MvpPresenter {
        fun updateFeedSubscriptions()
        fun showArticles(feed: Feed)
        fun showAddNewFeed()
    }
}