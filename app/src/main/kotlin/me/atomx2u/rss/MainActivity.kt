package me.atomx2u.rss

import android.os.Bundle
import me.atomx2u.rss.mvp.BaseActivity
import me.atomx2u.rss.mvp.BasePresenter
import me.atomx2u.rss.mvp.MvpPresenter
import me.atomx2u.rss.mvp.MvpView
import me.atomx2u.rss.ui.Navigator
import me.atomx2u.rss.ui.feed.subscription.UserSubscriptionFragment

class MainActivity : BaseActivity<MvpPresenter>(), MvpView {

    val navigator = Navigator(this, supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .add(R.id.activity_container, UserSubscriptionFragment(), UserSubscriptionFragment.TAG)
            .commit()
    }

    override fun newPresenter() = object: BasePresenter<MvpView>(this) {}
}
