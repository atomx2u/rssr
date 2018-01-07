package me.atomx2u.rss.ui.feed.add

import me.atomx2u.rss.base.IdentifiedView
import me.atomx2u.rss.base.ScopedPresenter

interface AddFeedContract {

    interface Presenter: ScopedPresenter {
        fun addNewFeedSubscription(feedLink: String)
    }

    interface View: IdentifiedView {
        fun showErrorHint(hint: CharSequence)
        fun clearErrorHint()
        fun switchLoading(isLoading: Boolean)
    }
}

