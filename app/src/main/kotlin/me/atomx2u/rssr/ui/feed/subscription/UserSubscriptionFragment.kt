package me.atomx2u.rssr.ui.feed.subscription

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import butterknife.OnClick
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_user_subscription.*
import me.atomx2u.rssr.MainActivity
import me.atomx2u.rssr.R
import me.atomx2u.rssr.dagger.App
import me.atomx2u.rssr.util.getColor
import me.atomx2u.rssr.mvp.BaseFragment
import me.atomx2u.rssr.ui.feed.addition.AddFeedPresenter
import me.atomx2u.rssr.ui.model.FeedViewModel
import me.atomx2u.rssr.util.ImageLoaderImpl

class UserSubscriptionFragment :
    BaseFragment<UserSubscriptionContract.View, UserSubscriptionContract.Presenter>(),
    AddFeedPresenter.OnNewFeedAdded {

    override val layoutRes: Int get() = R.layout.fragment_user_subscription

    override fun vView() = object : UserSubscriptionContract.View {
        override fun showFeedSubscriptions(feeds: List<FeedViewModel>) {
            adapter.setData(feeds)
        }
    }

    override fun presenter(context: Context): UserSubscriptionContract.Presenter {
        return UserSubscriptionPresenter(vView, (activity!!.applicationContext as App).repo, (activity as MainActivity).navigator)
    }

    private val destroyDisposable = CompositeDisposable()

    private var viewState: ViewState = ViewState.NONE
        set(value) {
            field = value
            when (value) {
                ViewState.ADD -> fab.apply {
                    backgroundTintList = ColorStateList.valueOf(getColor(R.color.colorAccent))
                    setImageResource(R.drawable.ic_add)

                }
                ViewState.DELETE -> fab.apply {
                    backgroundTintList = ColorStateList.valueOf(getColor(R.color.dangerRed))
                    setImageResource(R.drawable.ic_delete)
                }
                else -> {}
            }
        }

    private lateinit var adapter: FeedAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        setupSubscriptionRecyclerView()
        viewState = ViewState.ADD
        presenter.updateFavoriteFeedSubscriptions()
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as AppCompatActivity).setSupportActionBar(null)
        destroyDisposable.dispose()
    }

    override fun onNewFeedAdded() {
        presenter.updateFavoriteFeedSubscriptions()
    }

    private fun setupSubscriptionRecyclerView() {
        adapter = FeedAdapter(ImageLoaderImpl(activity!!.applicationContext)).apply {
            destroyDisposable.add(onItemClick().subscribe(::onUserSubscriptionItemClick))
            destroyDisposable.add(onItemLongClick().subscribe(::onUserSubscriptionItemLongClick))
        }
        subscriptions.adapter = adapter
        subscriptions.layoutManager = LinearLayoutManager(context)
    }

    @Suppress("unused")
    @OnClick(R.id.fab)
    internal fun onFabClick() {
        when (viewState) {
            ViewState.ADD -> presenter.showAddNewFeed()
            ViewState.DELETE -> adapter.selectedItem()?.let { presenter.unsubscribeFeed(it) }
            else -> {
            }
        }
    }

    private fun onUserSubscriptionItemClick(feed: FeedViewModel) = when (viewState) {
        ViewState.ADD -> presenter.showArticles(feed)
        ViewState.DELETE -> {
            adapter.clearSelection()
            viewState = ViewState.ADD
        }
        else -> {}
    }

    private fun onUserSubscriptionItemLongClick(feed: FeedViewModel) {
        viewState = ViewState.DELETE
        adapter.setSelection(feed)
    }

    companion object {
        val TAG: String = UserSubscriptionFragment::class.java.simpleName
    }

    enum class ViewState {
        ADD,
        DELETE,
        NONE
    }
}
