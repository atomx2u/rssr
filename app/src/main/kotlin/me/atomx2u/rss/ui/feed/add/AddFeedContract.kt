package me.atomx2u.rss.ui.feed.add

import me.atomx2u.rss.mvp.MvpView
import me.atomx2u.rss.mvp.MvpPresenter

interface AddFeedContract {

    interface Presenter: MvpPresenter {
        fun addNewFeedSubscription(feedLink: String)
    }

    interface View: MvpView {
        fun showErrorHint(hint: CharSequence)
        fun clearErrorHint()
        fun switchLoading(isLoading: Boolean)
    }
}

