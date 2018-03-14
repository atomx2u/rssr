package me.atomx2u.rssr.ui.article.content

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.fragment_article_content.*
import me.atomx2u.rssr.R
import me.atomx2u.rssr.mvp.BaseFragment
import me.atomx2u.rssr.mvp.MvpPresenter
import me.atomx2u.rssr.mvp.MvpView
import javax.inject.Inject

class ArticleContentFragment :
    BaseFragment<MvpView, MvpPresenter>(), MvpView {

    override val layoutRes: Int get() = R.layout.fragment_article_content

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val link = arguments!!.getString("link")
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(link)
    }

    companion object {
        val TAG: String = ArticleContentFragment::class.java.simpleName
        fun new() = ArticleContentFragment()
    }
}

class ViewController @Inject constructor() : MvpView