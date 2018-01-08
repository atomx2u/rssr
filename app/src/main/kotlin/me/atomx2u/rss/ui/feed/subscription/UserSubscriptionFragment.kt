package me.atomx2u.rss.ui.feed.subscription

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.atomx2u.rss.base.BaseFragment
import me.atomx2u.rss.domain.Feed

class UserSubscriptionFragment : BaseFragment<UserSubscriptionContract.Presenter>(), UserSubscriptionContract.View {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState) // TODO 画布局
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupSubscriptionRecyclerView()
    }

    private fun setupSubscriptionRecyclerView() {

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
