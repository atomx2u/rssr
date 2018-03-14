package me.atomx2u.rssr.ui.feed.addition

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import butterknife.OnClick
import kotlinx.android.synthetic.main.fragment_add_feed.*
import me.atomx2u.rssr.R
import me.atomx2u.rssr.mvp.BaseDialogFragment
import me.atomx2u.rssr.util.defaultMetrics
import javax.inject.Inject

class AddFeedFragment :
    BaseDialogFragment<AddFeedContract.View, AddFeedContract.Presenter>() {

    override val layoutRes: Int get() = R.layout.fragment_add_feed

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            window.requestFeature(Window.FEATURE_NO_TITLE)
        }
    }

    override fun onStart() {
        super.onStart()
        dialog.window?.setLayout((activity!!.defaultMetrics().widthPixels * 0.75).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    @OnClick(R.id.btnAdd)
    fun onBtnAddClick() {
        val feedLink = etFeedUrl.text.toString()
        presenter.addNewFeedSubscription(feedLink)
    }

    companion object {
        val TAG: String = AddFeedFragment::class.java.simpleName

        fun new(): AddFeedFragment = AddFeedFragment()
    }
}

class ViewController @Inject constructor(val instance: AddFeedFragment) : AddFeedContract.View {
    override fun showErrorHint(hint: CharSequence) {
        instance.tvError.apply {
            text = hint
            visibility = View.VISIBLE
        }
    }

    override fun clearErrorHint() {
        instance.tvError.apply {
            text = ""
            visibility = View.GONE
        }
    }

    override fun switchLoading(isLoading: Boolean) {
        instance.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun dismiss() {
        instance.dismiss()
    }
}