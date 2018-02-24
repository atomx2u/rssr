package me.atomx2u.rss.ui.feed.subscription

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_user_subscription.*
import me.atomx2u.rss.R
import me.atomx2u.rss.mvp.BaseFragment
import me.atomx2u.rss.domain.Feed
import me.atomx2u.rss.droidex.toast
import me.atomx2u.rss.util.ImageLoader

class UserSubscriptionFragment : BaseFragment<UserSubscriptionContract.Presenter>(), UserSubscriptionContract.View {

    private val destroyDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_subscription, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupToolbar()
        setupSubscriptionRecyclerView()
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
    }

    private fun setupSubscriptionRecyclerView() {
        val imageLoader = object : ImageLoader {
            override fun loadImage(url: String, target: ImageView, placeholder: Int, errorDrawable: Int) {

            }
        }
        val adapter =  FeedAdapter(imageLoader).apply {
            destroyDisposable.add(onItemClick().subscribe(::onUserSubscriptionItemClick))
            destroyDisposable.add(onItemLongClick().subscribe(::onUserSubscriptionItemLongClick))
        }
        subscriptions.adapter = adapter
        subscriptions.layoutManager = LinearLayoutManager(context)
        add.setOnClickListener(::onAddSubscriptionBtnClick)
    }

    override fun onDestroy() {
        super.onDestroy()
        unsetToolbar()
        destroyDisposable.dispose()
    }

    private fun unsetToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(null)
    }

    override fun showFeedSubscriptions(feeds: List<Feed>) {
        (subscriptions.adapter as? FeedAdapter)?.data?.onNext(feeds)
    }

    // nexus method
    private fun onAddSubscriptionBtnClick(v : View) {
        presenter.showAddNewFeed()
    }

    private fun onUserSubscriptionItemClick(feed: Feed) {
        presenter.showArticles(feed)
    }

    private fun onUserSubscriptionItemLongClick(feed: Feed) {
        toast("emm..")
    }

    companion object {
        const val TAG = "UserSubscriptionFragment"
    }
}
