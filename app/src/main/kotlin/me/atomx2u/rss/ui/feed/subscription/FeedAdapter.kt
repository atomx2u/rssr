package me.atomx2u.rss.ui.feed.subscription

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import me.atomx2u.rss.R
import me.atomx2u.rss.domain.Feed
import me.atomx2u.rss.util.ImageLoader

class FeedAdapter(private val imageLoader: ImageLoader) : RecyclerView.Adapter<FeedAdapter.ViewHolder>() {

    // TODO FeedViewModel
    val data: MutableList<Feed> = ArrayList()
    // TODO onClickRx

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_feed, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.setItem(data[position])

    fun updateFeeds(newData: List<Feed>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image)
        val title: TextView = itemView.findViewById(R.id.title)
        val description: TextView = itemView.findViewById(R.id.description)

        fun setItem(feed: Feed) {
            imageLoader.loadImage(feed.imageLink, image, R.drawable.feed_icon, R.drawable.feed_icon) // TODO
            title.text = feed.title
            description.text = feed.description
        }
    }
}
