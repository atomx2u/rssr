package me.atomx2u.rssr.ui.feed.addition

import me.atomx2u.rssr.mvp.AsDialog
import me.atomx2u.rssr.mvp.MvpView
import me.atomx2u.rssr.mvp.MvpPresenter

interface AddFeedContract {

    interface Presenter: MvpPresenter {
        fun addNewFeedSubscription(feedLink: String)
    }

    interface View: MvpView, AsDialog {
        fun showErrorHint(hint: CharSequence)
        fun clearErrorHint()
        fun switchLoading(isLoading: Boolean)
    }
}

