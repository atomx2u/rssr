package me.atomx2u.rss.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import me.atomx2u.rss.ui.article.content.ArticleContentFragment
import me.atomx2u.rss.ui.article.list.ArticlesFragment
import me.atomx2u.rss.ui.feed.addition.AddFeedFragment

class Navigator(
    private val activity: AppCompatActivity,
    private val fragmentManager: android.support.v4.app.FragmentManager
) {

    fun refreshUserSubscriptionFragment() {

    }

    fun showAddFeedFragment() = transactionAdd(AddFeedFragment.newInstance(), AddFeedFragment.TAG)

    fun showArticles(feedId: Long) {
        val fragment = ArticlesFragment.newInstance()
        fragment.arguments = Bundle().apply {
            putLong("feedId", feedId)
        }
        transactionAdd(fragment, ArticlesFragment.TAG)
    }

    fun showArticleDetail(link: String) {
        val fragment = ArticleContentFragment.newInstance()
        fragment.arguments = Bundle().apply {
            putString("link", link)
        }
        transactionAdd(fragment, ArticleContentFragment.TAG)
    }

    fun back() {
        if (fragmentManager.backStackEntryCount == 0) {
            activity.finish()
        } else {
            fragmentManager.popBackStack()
        }
    }

    private fun transactionAdd(fragment: Fragment, tag: String) {
        fragmentManager.beginTransaction()
            .add(fragment, tag)
            .addToBackStack(null)
            .commit()
    }
}