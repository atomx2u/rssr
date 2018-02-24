package me.atomx2u.rss.mvp

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import io.reactivex.android.schedulers.AndroidSchedulers
import java.lang.ref.WeakReference

abstract class BasePresenter<View : MvpView>(view: View) : MvpPresenter {
    val view: WeakReference<View> = WeakReference(view)
    val actionQueue = RxViewActionQueue(AndroidSchedulers.mainThread())

    override fun create() {
    }

    override fun resume() {
        actionQueue.resume()
    }

    override fun pause() {
        actionQueue.pause()
    }

    override fun destroy() {
        actionQueue.destory()
    }
}


/**
 * BaseFragment 并不是一个 View，而是一个 P, V 关系的联结(nexus)。
 * 可以将 BaseFragment 同时作为一个 View 使用；也可以单独实现一个 MvpView
 * */
abstract class BaseFragment<Presenter : MvpPresenter>
    : Fragment(), MvpNexus {

    lateinit var presenter: Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.create()
    }

    override fun onResume() {
        super.onResume()
        presenter.resume()
    }

    override fun onPause() {
        super.onPause()
        presenter.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }

    override fun onBack() {
        presenter.back()
    }
}


abstract class BaseDialogFragment<Presenter: MvpPresenter>
    : DialogFragment(), MvpNexus {

    lateinit var presenter: Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.create()
    }

    override fun onResume() {
        super.onResume()
        presenter.resume()
    }

    override fun onPause() {
        super.onPause()
        presenter.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }

    override fun onBack() {
        presenter.back()
    }
}
