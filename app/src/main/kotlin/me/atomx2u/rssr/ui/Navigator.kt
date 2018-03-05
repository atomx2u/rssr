package me.atomx2u.rssr.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import me.atomx2u.rssr.R
import me.atomx2u.rssr.ui.article.content.ArticleContentFragment
import me.atomx2u.rssr.ui.article.list.ArticlesFragment
import me.atomx2u.rssr.ui.feed.addition.AddFeedFragment

class Navigator(
    private val activity: AppCompatActivity,
    private val fragmentManager: android.support.v4.app.FragmentManager
) {

    fun showAddFeed() {
        // 并不会加入 back stack
        AddFeedFragment.new().show(fragmentManager, AddFeedFragment.TAG)
//        AddFeedFragment.new().let {
//            fragmentManager.beginTransaction().add(it, AddFeedFragment.TAG)
//                .addToBackStack(AddFeedFragment.TAG)
//                .commit()
//        }
    }

    fun showArticles(feedId: Long) {
        val fragment = ArticlesFragment.newInstance()
        fragment.arguments = Bundle().apply {
            putLong("feedId", feedId)
        }
        replace(fragment, ArticlesFragment.TAG)
    }

    fun showArticleDetail(link: String) {
        val fragment = ArticleContentFragment.newInstance()
        fragment.arguments = Bundle().apply {
            putString("link", link)
        }
        replace(fragment, ArticleContentFragment.TAG)
    }

    fun back() {
        if (fragmentManager.backStackEntryCount == 0) {
            activity.finish()
        } else {
            fragmentManager.popBackStack()
        }
    }

    private fun replace(fragment: Fragment, tag: String) {
        fragmentManager.beginTransaction()
            .replace(R.id.activity_container, fragment, tag)
            .addToBackStack(null)
            .commit()
    }
}