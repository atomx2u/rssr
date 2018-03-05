package me.atomx2u.rssr.ui.feed.subscription

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_user_subscription.*
import me.atomx2u.rssr.MainActivity
import me.atomx2u.rssr.R
import me.atomx2u.rssr.dagger.App
import me.atomx2u.rssr.mvp.BaseFragment
import me.atomx2u.rssr.domain.Feed
import me.atomx2u.rssr.droidex.toast
import me.atomx2u.rssr.ui.feed.addition.AddFeedPresenter
import me.atomx2u.rssr.util.ImageLoaderImpl

class UserSubscriptionFragment :
    BaseFragment<UserSubscriptionContract.Presenter>(),
    UserSubscriptionContract.View,
    AddFeedPresenter.OnNewFeedAdded {

    override fun onNewFeedAdded() {
        presenter.updateFeedSubscriptions()
    }

    private val destroyDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_subscription, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupSubscriptionRecyclerView()
        presenter.updateFeedSubscriptions()
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
    }

    private fun setupSubscriptionRecyclerView() {
        val adapter =  FeedAdapter(ImageLoaderImpl(activity!!.applicationContext)).apply {
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

    override fun newPresenter(context: Context): UserSubscriptionContract.Presenter {
        return UserSubscriptionPresenter(this, (activity!!.applicationContext as App).repo, (activity as MainActivity).navigator)
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
        val TAG: String = UserSubscriptionFragment::class.java.simpleName
    }
}
