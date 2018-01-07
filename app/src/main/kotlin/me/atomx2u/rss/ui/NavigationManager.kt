package me.atomx2u.rss.ui

import android.support.v7.app.AppCompatActivity
import me.atomx2u.rss.ui.feed.add.AddFeedFragment
import me.atomx2u.rss.ui.feed.subscription.UserSubscriptionFragment

class NavigationManager(
    private val activity: AppCompatActivity,
    private val fragmentManager: android.support.v4.app.FragmentManager
) {

    fun refreshUserSubscriptionFragment() {
        fragmentManager.findFragmentByTag(UserSubscriptionFragment.TAG)?.let {
            (it as UserSubscriptionFragment).controller.refreshUserSubscriptions()
        }
    }

    fun showAddFeedFragment() {
        fragmentManager.beginTransaction()
            .add(AddFeedFragment.newInstance(), AddFeedFragment.TAG)
            .addToBackStack(null)
            .commit()
    }

    fun back() {
        if (fragmentManager.backStackEntryCount == 0) {
            activity.finish()
        } else {
            fragmentManager.popBackStack()
        }
    }
}