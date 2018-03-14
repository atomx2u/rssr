package me.atomx2u.rssr.ui.feed.subscription

import me.atomx2u.rssr.mvp.MvpPresenter
import me.atomx2u.rssr.mvp.MvpView
import me.atomx2u.rssr.ui.feed.FeedViewModel

interface UserSubscriptionContract {

    interface View : MvpView {
        fun showFeedSubscriptions(feeds: List<FeedViewModel>)
    }

    interface Presenter : MvpPresenter {
        fun updateFavoriteFeedSubscriptions()
        fun unsubscribeFeed(feed: FeedViewModel)
        fun showArticles(feed: FeedViewModel)
        fun showAddNewFeed()
    }
}