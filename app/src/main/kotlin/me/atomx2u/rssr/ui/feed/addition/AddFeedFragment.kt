package me.atomx2u.rssr.ui.feed.addition

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import butterknife.BindView
import butterknife.OnClick
import kotlinx.android.synthetic.main.fragment_add_feed.*
import me.atomx2u.rssr.MainActivity
import me.atomx2u.rssr.R
import me.atomx2u.rssr.dagger.App
import me.atomx2u.rssr.mvp.BaseDialogFragment
import me.atomx2u.rssr.data.component.FeedValidatorImpl
import me.atomx2u.rssr.util.defaultMetrics
import me.atomx2u.rssr.ui.feed.subscription.UserSubscriptionFragment
import java.lang.ref.WeakReference

class AddFeedFragment :
    BaseDialogFragment<AddFeedContract.View, AddFeedContract.Presenter>() {

    override val layoutRes: Int get() = R.layout.fragment_add_feed

    override fun vView() = object :  AddFeedContract.View {
        override fun showErrorHint(hint: CharSequence) {
            tvError.apply {
                text = hint
                visibility = View.VISIBLE
            }
        }

        override fun clearErrorHint() {
            tvError.apply {
                text = ""
                visibility = View.GONE
            }
        }

        override fun switchLoading(isLoading: Boolean) {
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        override fun dismiss() {
            this@AddFeedFragment.dismiss()
        }
    }

    override fun presenter(context: Context): AddFeedContract.Presenter {
        return AddFeedPresenter(vView,
            (activity as MainActivity).navigator,
            FeedValidatorImpl(),
            (activity!!.applicationContext as App).prefs,
            (activity!!.applicationContext as App).timeUtils)
            .apply {
                val onNewFeedAddedImpl = fragmentManager?.findFragmentByTag(UserSubscriptionFragment.TAG)
                    as? AddFeedPresenter.OnNewFeedAdded
                if (onNewFeedAddedImpl != null) {
                    onNewFeedAdded = WeakReference(onNewFeedAddedImpl)
                }
            }
    }

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

        fun new(): AddFeedFragment {
            return AddFeedFragment()
        }
    }
}