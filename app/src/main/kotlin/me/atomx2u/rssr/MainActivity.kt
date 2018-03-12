package me.atomx2u.rssr

import android.os.Bundle
import dagger.android.AndroidInjection
import me.atomx2u.rssr.mvp.BaseActivity
import me.atomx2u.rssr.mvp.BasePresenter
import me.atomx2u.rssr.mvp.MvpPresenter
import me.atomx2u.rssr.mvp.MvpView
import me.atomx2u.rssr.ui.Navigator
import me.atomx2u.rssr.ui.feed.subscription.UserSubscriptionFragment

class MainActivity : BaseActivity<MvpView, MvpPresenter>(), MvpView {

    override val layoutRes: Int get() = R.layout.activity_main

//    override fun vView() = object : MvpView {}
//
//    override fun presenter() =

    val navigator = Navigator(this, supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction()
            .add(R.id.activity_container, UserSubscriptionFragment(), UserSubscriptionFragment.TAG)
            .commit()
    }

    override fun inject() = AndroidInjection.inject(this)
}
