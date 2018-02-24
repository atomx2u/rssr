package me.atomx2u.rss.ui.article.list

import me.atomx2u.rss.mvp.BaseFragment
import me.atomx2u.rss.mvp.MvpPresenter

class ArticlesFragment : BaseFragment<MvpPresenter>() {
    override fun getViewId(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        const val TAG = "ArticlesFragment"
        fun newInstance() = ArticlesFragment()
    }
}