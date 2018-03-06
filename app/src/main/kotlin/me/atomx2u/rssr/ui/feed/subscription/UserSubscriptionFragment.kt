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
import me.atomx2u.rssr.ui.feed.addition.AddFeedPresenter
import me.atomx2u.rssr.ui.model.FeedViewModel
import me.atomx2u.rssr.util.ImageLoaderImpl

class UserSubscriptionFragment :
    BaseFragment<UserSubscriptionContract.Presenter>(),
    UserSubscriptionContract.View,
    AddFeedPresenter.OnNewFeedAdded {

    enum class ViewState {
        ADD,
        DELETE
    }

    var viewState: ViewState = ViewState.ADD
        set(value) {
            field = value
            when (value) {
                ViewState.ADD -> setupAddViewState()
                ViewState.DELETE -> setupDeleteViewState()
            }
        }

    lateinit var adapter: FeedAdapter

    private fun setupAddViewState() {}
    private fun setupDeleteViewState() {}

    override fun onNewFeedAdded() {
        presenter.updateFavoriteFeedSubscriptions()
    }

    private val destroyDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_subscription, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupSubscriptionRecyclerView()
        presenter.updateFavoriteFeedSubscriptions()
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
    }

    private fun setupSubscriptionRecyclerView() {
        adapter =  FeedAdapter(ImageLoaderImpl(activity!!.applicationContext)).apply {
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

    override fun showFeedSubscriptions(feeds: List<FeedViewModel>) {
        adapter.data.clear()
        adapter.data.addAll(feeds)
        adapter.notifyDataSetChanged()
    }

    // nexus method
    private fun onAddSubscriptionBtnClick(v : View) {
        when (viewState) {
            ViewState.ADD -> presenter.showAddNewFeed()
            ViewState.DELETE -> adapter.selectedItem()?.let { presenter.unsubscribeFeed(it) }
        }

    }

    private fun onUserSubscriptionItemClick(feed: FeedViewModel) {
        when (viewState) {
            ViewState.ADD -> presenter.showArticles(feed)
            ViewState.DELETE -> {
                adapter.clearSelection()
                viewState = ViewState.ADD
            }
        }
    }

    private fun onUserSubscriptionItemLongClick(feed: FeedViewModel) {
        viewState = ViewState.DELETE
        adapter.setSelection(feed)
    }

    companion object {
        val TAG: String = UserSubscriptionFragment::class.java.simpleName
    }
}
