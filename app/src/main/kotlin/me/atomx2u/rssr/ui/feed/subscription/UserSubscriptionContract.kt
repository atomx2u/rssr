package me.atomx2u.rssr.ui.feed.subscription

import me.atomx2u.rssr.mvp.MvpPresenter
import me.atomx2u.rssr.domain.Feed
import me.atomx2u.rssr.mvp.MvpView

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