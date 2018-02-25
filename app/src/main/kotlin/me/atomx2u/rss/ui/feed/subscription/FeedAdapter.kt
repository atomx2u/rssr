package me.atomx2u.rss.ui.feed.subscription

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import me.atomx2u.rss.R
import me.atomx2u.rss.domain.Feed
import me.atomx2u.rss.util.ImageLoader
import java.util.concurrent.TimeUnit

class FeedAdapter(private val imageLoader: ImageLoader) : RecyclerView.Adapter<FeedAdapter.ViewHolder>() {

    // --- input // TODO solve leak；好像不会 leak，跑起来后测试一下
    val data: BehaviorSubject<List<Feed>> = BehaviorSubject.create<List<Feed>>()
    // --- output
    fun onItemClick(): Observable<Feed> = onItemClickSubject.throttleFirst(50, TimeUnit.SECONDS)
    fun onItemLongClick(): Observable<Feed> = onItemLongClickSubject.throttleFirst(50, TimeUnit.SECONDS)

    private val onItemClickSubject = PublishSubject.create<Feed>()
    private val onItemLongClickSubject = PublishSubject.create<Feed>()

    // TODO FeedViewModel
    init {
        data.subscribe { notifyDataSetChanged() }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_feed, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = data.value.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.setItem(data.value[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image)
        val title: TextView = itemView.findViewById(R.id.title)
        val description: TextView = itemView.findViewById(R.id.description)

        init {
            itemView.setOnClickListener { onItemClickSubject.onNext(data.value[adapterPosition]) }
            itemView.setOnLongClickListener { onItemLongClickSubject.onNext(data.value[adapterPosition]); true }
        }

        fun setItem(feed: Feed) {
            imageLoader.loadImage(feed.imageLink, image, R.drawable.feed_icon, R.drawable.feed_icon)
            title.text = feed.title
            description.text = feed.description
        }
    }
}
