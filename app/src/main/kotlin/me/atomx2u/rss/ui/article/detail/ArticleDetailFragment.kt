package me.atomx2u.rss.ui.article.detail

import me.atomx2u.rss.mvp.BaseFragment
import me.atomx2u.rss.mvp.MvpPresenter

class ArticleDetailFragment : BaseFragment<MvpPresenter>() {
    override fun getViewId(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        const val TAG = "ArticleDetailFragment"
        fun newInstance() = ArticleDetailFragment()
    }
}