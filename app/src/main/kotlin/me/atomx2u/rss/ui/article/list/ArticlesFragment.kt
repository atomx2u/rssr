package me.atomx2u.rss.ui.article.list

import me.atomx2u.rss.base.BaseFragment
import me.atomx2u.rss.base.ScopedPresenter

class ArticlesFragment : BaseFragment<ScopedPresenter>() {
    override fun getViewId(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        const val TAG = "ArticlesFragment"
        fun newInstance() = ArticlesFragment()
    }
}