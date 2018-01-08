package me.atomx2u.rss.ui.article.detail

import me.atomx2u.rss.base.BaseFragment
import me.atomx2u.rss.base.ScopedPresenter

class ArticleDetailFragment : BaseFragment<ScopedPresenter>() {
    override fun getViewId(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        const val TAG = "ArticleDetailFragment"
        fun newInstance() = ArticleDetailFragment()
    }
}