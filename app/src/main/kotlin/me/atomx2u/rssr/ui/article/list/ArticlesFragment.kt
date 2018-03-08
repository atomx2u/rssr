package me.atomx2u.rssr.ui.article.list

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_articles.*
import me.atomx2u.rssr.MainActivity
import me.atomx2u.rssr.R
import me.atomx2u.rssr.dagger.App
import me.atomx2u.rssr.domain.Article
import me.atomx2u.rssr.mvp.BaseFragment

class ArticlesFragment
    : BaseFragment<ArticlesContract.View, ArticlesContract.Presenter>() {

    override val layoutRes: Int get() = R.layout.fragment_articles

    override fun vView() = object : ArticlesContract.View {
        override fun showArticles(_articles: List<Article>) {
            (articles.adapter as? ArticleAdapter)?.data?.onNext(_articles)
        }
    }

    override fun presenter(context: Context) =
        ArticlesPresenter(vView,
            (activity as MainActivity).navigator, (activity!!.applicationContext as App).repo)

    private val destroyDisposable = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ArticleAdapter()
        destroyDisposable.add(
            adapter.onItemClick().subscribe {
                presenter.showArticleContent(it)
            }
        )
        articles.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        articles.adapter = adapter
        presenter.showArticles(arguments!!.getLong("feedId"))
    }

    companion object {
        val TAG: String = ArticlesFragment::class.java.simpleName
        fun newInstance() = ArticlesFragment()
    }
}
