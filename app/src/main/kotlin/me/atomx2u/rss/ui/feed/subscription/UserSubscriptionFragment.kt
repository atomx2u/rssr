package me.atomx2u.rss.ui.feed.subscription

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import kotlinx.android.synthetic.main.fragment_user_subscription.*
import me.atomx2u.rss.R
import me.atomx2u.rss.base.BaseFragment
import me.atomx2u.rss.domain.Feed
import me.atomx2u.rss.util.ImageLoader

class UserSubscriptionFragment : BaseFragment<UserSubscriptionContract.Presenter>(), UserSubscriptionContract.View {

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
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
        subscriptions.adapter = FeedAdapter(imageLoader)
        subscriptions.layoutManager = LinearLayoutManager(context)
    }

    override fun onDestroy() {
        super.onDestroy()
        unsetToolbar()
    }

    private fun unsetToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(null)
    }

    override fun showFeedSubscriptions(feeds: List<Feed>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getViewId(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun onAddSubscriptionBtnClick() {

    }

    private fun onUserSubscriptionItemClick() {

    }

    private fun onUserSubscriptionItemLongClick() {

    }

    val controller = Controller()

    inner class Controller {

        fun refreshUserSubscriptions() {
        }
    }

    companion object {
        const val TAG = "UserSubscriptionFragment"
    }
}
