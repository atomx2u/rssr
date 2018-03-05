package me.atomx2u.rssr.ui.article.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import me.atomx2u.rssr.R
import me.atomx2u.rssr.domain.Article
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    val data: BehaviorSubject<List<Article>> = BehaviorSubject.createDefault(emptyList())

    fun onItemClick(): Observable<Article> = onItemClickSubject.throttleFirst(50, TimeUnit.SECONDS)

    init {
        data.subscribe {
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        )
    }

    override fun getItemCount() = data.value.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.setArticle(data.value[position])
    }

    private val onItemClickSubject = PublishSubject.create<Article>()
    private val dateFormatter = SimpleDateFormat("YYYY-MM-dd", Locale.SIMPLIFIED_CHINESE)
    private fun Long.formatDate(): String {
        return dateFormatter.format(Date(this))
    }

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.article_title)
        val date: TextView = itemView.findViewById(R.id.article_date)
        val newIndicator: TextView = itemView.findViewById(R.id.article_new_indicator)
        val favouriteIndicator: ImageView = itemView.findViewById(R.id.article_favourite_indicator)

        init {
            itemView.setOnClickListener {
                onItemClickSubject.onNext(data.value[adapterPosition])
            }
        }

        fun setArticle(article: Article) {
            title.text = article.title
            date.text = article.pubDate.formatDate()
            newIndicator // TODO
            favouriteIndicator
        }
    }
}