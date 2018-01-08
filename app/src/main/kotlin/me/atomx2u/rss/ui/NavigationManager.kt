package me.atomx2u.rss.ui

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import me.atomx2u.rss.ui.article.detail.ArticleDetailFragment
import me.atomx2u.rss.ui.article.list.ArticlesFragment
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

    fun showAddFeedFragment() = addTransaction(AddFeedFragment.newInstance(), AddFeedFragment.TAG)

    fun showArticles(feedId: Long) = addTransaction(ArticlesFragment.newInstance(), ArticlesFragment.TAG)

    fun showArticleDetail(articleId: Long) = addTransaction(ArticleDetailFragment.newInstance(), ArticleDetailFragment.TAG)

    private fun addTransaction(fragment: Fragment, tag: String) {
        fragmentManager.beginTransaction()
            .add(fragment, tag)
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