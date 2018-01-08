package me.atomx2u.rss.ui.feed.subscription

import me.atomx2u.rss.base.IdentifiedView
import me.atomx2u.rss.base.ScopedPresenter
import me.atomx2u.rss.domain.Feed

interface UserSubscriptionContract {

    interface View : IdentifiedView {
        fun showFeedSubscriptions(feeds: List<Feed>)
    }

    interface Presenter : ScopedPresenter {
        fun updateFeedSubscriptions()
        fun showArticles(feed: Feed)
        fun showAddNewFeed()
    }
}