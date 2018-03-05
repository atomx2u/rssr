package me.atomx2u.rssr.ui.article.content

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.fragment_article_content.*
import me.atomx2u.rssr.MainActivity
import me.atomx2u.rssr.R
import me.atomx2u.rssr.mvp.BaseFragment
import me.atomx2u.rssr.mvp.MvpPresenter
import me.atomx2u.rssr.mvp.MvpView

class ArticleContentFragment : BaseFragment<MvpPresenter>(), MvpView {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_article_content, container, false)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val link = arguments!!.getString("link")
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(link)
    }

    override fun newPresenter(context: Context) = ArticleContentPresenter(this, (activity as MainActivity).navigator)

    companion object {
        val TAG: String = ArticleContentFragment::class.java.simpleName
        fun newInstance() = ArticleContentFragment()
    }
}