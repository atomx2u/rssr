package me.atomx2u.rss.base

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import java.lang.ref.WeakReference

interface IdentifiedView {
    fun getViewId(): String
}

interface ScopedPresenter {
    fun create()
    fun resume()
    fun pause()
    fun destroy()
    fun back()
}

interface BackPropagatingFragment {
    fun onBack()
}

abstract class BasePresenter<View : IdentifiedView>(view: View) : ScopedPresenter {
    var view: WeakReference<View> = WeakReference(view)
}

abstract class BaseFragment<Presenter: ScopedPresenter>
    : Fragment(), BackPropagatingFragment, IdentifiedView {

    private lateinit var presenter: Presenter

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

abstract class BaseDialogFragment<Presenter: ScopedPresenter>
    : DialogFragment(), BackPropagatingFragment, IdentifiedView {

    private lateinit var presenter: Presenter

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