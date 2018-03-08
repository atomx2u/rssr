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

class ArticleContentFragment :
    BaseFragment<MvpView, MvpPresenter>(), MvpView {

    override val layoutRes: Int get() = R.layout.fragment_article_content

    override fun vView() = object : MvpView {}

    override fun presenter(context: Context): MvpPresenter {
        return ArticleContentPresenter(this, (activity as MainActivity).navigator)
    }

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
        fun newInstance() = ArticleContentFragment()
    }
}