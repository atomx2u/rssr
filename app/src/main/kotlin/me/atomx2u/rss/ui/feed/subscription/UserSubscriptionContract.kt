package me.atomx2u.rss.ui.feed.subscription

import me.atomx2u.rss.domain.Feed

interface UserSubscriptionContract {

    interface View {
        fun showFeedSubscriptions(feeds: Feed)
    }

    interface Presenter {
        fun updateFeedSubscriptions()
        fun showArticles(feed: Feed)
        fun showAddNewFeed()
    }
}