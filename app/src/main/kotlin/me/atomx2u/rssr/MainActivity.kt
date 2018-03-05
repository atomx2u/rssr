package me.atomx2u.rssr

import android.os.Bundle
import me.atomx2u.rssr.mvp.BaseActivity
import me.atomx2u.rssr.mvp.BasePresenter
import me.atomx2u.rssr.mvp.MvpPresenter
import me.atomx2u.rssr.mvp.MvpView
import me.atomx2u.rssr.ui.Navigator
import me.atomx2u.rssr.ui.feed.subscription.UserSubscriptionFragment

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
