package me.atomx2u.rss.ui.feed.subscription

import android.support.v4.app.Fragment

class UserSubscriptionFragment : Fragment() {

    val controller = Controller()

    inner class Controller {

        fun refreshUserSubscriptions() {
        }
    }

    companion object {
        const val TAG = "UserSubscriptionFragment"
    }
}
