package me.atomx2u.rss.ui.article.list

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_articles.*
import me.atomx2u.rss.R
import me.atomx2u.rss.domain.Article
import me.atomx2u.rss.mvp.BaseFragment

class ArticlesFragment : BaseFragment<ArticlesContract.Presenter>(), ArticlesContract.View {

    private val destroyDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_articles, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        presenter = ArticlesPresenter(this, TODO, TODO)
        val adapter = ArticleAdapter()
        destroyDisposable.add(
            adapter.onItemClick().subscribe {
                presenter.showArticleContent(it)
            }
        )
        articles.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        articles.adapter = adapter
    }

    override fun showArticles(_articles: List<Article>) {
        (articles.adapter as? ArticleAdapter)?.data?.onNext(_articles)
    }

    companion object {
        val TAG: String = ArticlesFragment::class.java.simpleName
        fun newInstance() = ArticlesFragment()
    }
}
