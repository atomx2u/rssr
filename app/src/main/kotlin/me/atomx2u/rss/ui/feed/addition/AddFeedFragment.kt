package me.atomx2u.rss.ui.feed.addition

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import kotlinx.android.synthetic.main.fragment_add_feed.*
import me.atomx2u.rss.MainActivity
import me.atomx2u.rss.R
import me.atomx2u.rss.dagger.App
import me.atomx2u.rss.mvp.BaseDialogFragment
import me.atomx2u.rss.data.component.FeedValidatorImpl
import me.atomx2u.rss.droidex.defaultMetrics
import me.atomx2u.rss.ui.feed.subscription.UserSubscriptionFragment
import java.lang.ref.WeakReference

class AddFeedFragment :
    BaseDialogFragment<AddFeedContract.Presenter>(),
    AddFeedContract.View {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_feed, container)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            window.requestFeature(Window.FEATURE_NO_TITLE)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnAdd.setOnClickListener(onBtnAddClick)
    }

    override fun onStart() {
        super.onStart()
        dialog.window?.setLayout((activity!!.defaultMetrics().widthPixels * 0.75).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun newPresenter(context: Context) = AddFeedPresenter(this,
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

    private val onBtnAddClick = { _ : View ->
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