package me.atomx2u.rssr.ui.feed.subscription

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import me.atomx2u.rssr.R
import me.atomx2u.rssr.ui.model.FeedViewModel
import me.atomx2u.rssr.util.ImageLoader
import java.util.concurrent.TimeUnit

class FeedAdapter(private val imageLoader: ImageLoader) : RecyclerView.Adapter<FeedAdapter.ViewHolder>() {

    fun onItemClick(): Observable<FeedViewModel> = onItemClickSubject.throttleFirst(50, TimeUnit.MICROSECONDS)
    fun onItemLongClick(): Observable<FeedViewModel> = onItemLongClickSubject.throttleFirst(50, TimeUnit.MICROSECONDS)

    private val data: MutableList<FeedViewModel> = ArrayList()
    private var selectedPosition = -1

    fun setData(data: List<FeedViewModel>) {
        this.data.clear()
        this.data.addAll(data)
        selectedPosition = -1
        notifyDataSetChanged()
    }

    fun clearSelection() {
        if (selectedPosition < 0 || selectedPosition >= data.size)
            return
        data[selectedPosition] = data[selectedPosition].copy(isSelected = false)
        notifyItemChanged(selectedPosition)
        selectedPosition = -1
    }

    fun setSelection(feed: FeedViewModel) {
        clearSelection()
        selectedPosition = data.indexOf(feed)
        if (selectedPosition == -1 ) {
           return
        }
        data[selectedPosition] = data[selectedPosition].copy(isSelected = true)
        notifyItemChanged(selectedPosition)
    }

    fun selectedItem() = if (selectedPosition < 0) null else data[selectedPosition]

    private val onItemClickSubject = PublishSubject.create<FeedViewModel>()
    private val onItemLongClickSubject = PublishSubject.create<FeedViewModel>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_feed, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.setItem(data[position])


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image)
        val title: TextView = itemView.findViewById(R.id.title)
        val description: TextView = itemView.findViewById(R.id.description)

        init {
            itemView.setOnClickListener { onItemClickSubject.onNext(data[adapterPosition]) }
            itemView.setOnLongClickListener { onItemLongClickSubject.onNext(data[adapterPosition]); true }
        }

        fun setItem(feed: FeedViewModel) {
            imageLoader.loadImage(feed.imageLink, image, R.drawable.feed_icon, R.drawable.feed_icon)
            title.text = feed.title
            description.text = feed.description
            itemView.setBackgroundColor(Color.parseColor(
                if (feed.isSelected) "#880f0f0f"
                else "#00000000"
            ))
        }
    }
}
