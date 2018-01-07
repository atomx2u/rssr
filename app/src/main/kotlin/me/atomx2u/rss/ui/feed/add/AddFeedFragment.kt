package me.atomx2u.rss.ui.feed.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_add_feed.*
import me.atomx2u.rss.MainActivity
import me.atomx2u.rss.R
import me.atomx2u.rss.base.BaseDialogFragment
import me.atomx2u.rss.data.component.FeedValidatorImpl
import me.atomx2u.rss.data.preference.Prefs
import me.atomx2u.rss.data.util.TimeUtilsImpl

class AddFeedFragment : BaseDialogFragment<AddFeedContract.Presenter>(), AddFeedContract.View {

    private lateinit var presenter: AddFeedPresenter

    override fun getViewId(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_feed, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainActivity = (activity as MainActivity)
        presenter = AddFeedPresenter(this, mainActivity.navigationManager,
            FeedValidatorImpl(), Prefs(context!!.applicationContext), TimeUtilsImpl())

        btnAdd.setOnClickListener(this::onBtnAddClick)
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

    private fun onBtnAddClick(v: View) {
        val feedLink = etFeedUrl.text.toString()
        presenter.addNewFeedSubscription(feedLink)
    }

    companion object {
        const val TAG = "AddFeedFragment"

        fun newInstance(): AddFeedFragment {
            return AddFeedFragment()
        }
    }
}