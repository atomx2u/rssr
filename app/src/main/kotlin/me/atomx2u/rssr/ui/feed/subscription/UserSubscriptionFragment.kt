package me.atomx2u.rssr.ui.feed.subscription

import android.content.res.ColorStateList
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import butterknife.OnClick
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_user_subscription.*
import me.atomx2u.rssr.R
import me.atomx2u.rssr.mvp.BaseFragment
import me.atomx2u.rssr.ui.MainActivityEvent
import me.atomx2u.rssr.ui.feed.FeedViewModel
import me.atomx2u.rssr.util.getColor
import javax.inject.Inject

class UserSubscriptionFragment :
    BaseFragment<UserSubscriptionContract.View, UserSubscriptionContract.Presenter>() {

    override val layoutRes: Int get() = R.layout.fragment_user_subscription

    @Inject
    lateinit var eventRelay: PublishRelay<MainActivityEvent>
    @Inject
    lateinit var destroyDisposable: CompositeDisposable
    @Inject
    lateinit var adapter: FeedAdapter

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
                else -> {
                }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        setupSubscriptionRecyclerView()
        viewState = ViewState.ADD
        presenter.updateFavoriteFeedSubscriptions()
        destroyDisposable.add(
            eventRelay.subscribe {
                presenter.updateFavoriteFeedSubscriptions()
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as AppCompatActivity).setSupportActionBar(null)
        destroyDisposable.dispose()
    }

    private fun setupSubscriptionRecyclerView() {
        adapter.apply {
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
        else -> {
        }
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

class ViewController @Inject constructor(val instance: UserSubscriptionFragment) : UserSubscriptionContract.View {
    override fun showFeedSubscriptions(feeds: List<FeedViewModel>) {
        instance.adapter.setData(feeds)
    }
}