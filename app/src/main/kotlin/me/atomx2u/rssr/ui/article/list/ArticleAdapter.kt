package me.atomx2u.rssr.ui.article.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import me.atomx2u.rssr.R
import me.atomx2u.rssr.domain.model.Article
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    val data: MutableList<Article> = LinkedList()

    fun onItemClick(): Observable<Article> = itemClickSubject.throttleFirst(50, TimeUnit.MILLISECONDS)
    fun onFavoriteClick(): Observable<Article> = favouriteClickSubject.throttleFirst(50, TimeUnit.MILLISECONDS)
        .doOnNext(::toggleArticleFavoriteStatus)

    fun addData(data: List<Article>) {
        val start = this.data.size
        val count = data.count()
        this.data.addAll(data)
        notifyItemRangeInserted(start, count)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.setArticle(data[position])
    }

    private val itemClickSubject = PublishRelay.create<Article>()
    private val favouriteClickSubject = PublishRelay.create<Article>()
    private val dateFormatter = SimpleDateFormat("YYYY-MM-dd", Locale.SIMPLIFIED_CHINESE)
    private fun Long.formatDate(): String {
        return dateFormatter.format(Date(this))
    }

    private fun toggleArticleFavoriteStatus(article: Article) {
        val index = data.indexOf(article)
        val newValue = article.copy(isFavorite = !article.isFavorite)
        data[index] = newValue
        notifyItemChanged(index)
    }

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.article_title)
        lateinit var title: TextView

        @BindView(R.id.article_date)
        lateinit var  date: TextView

        @BindView(R.id.article_new_indicator)
        lateinit var  newIndicator: TextView

        @BindView(R.id.article_favourite_indicator)
        lateinit var favouriteIndicator: ImageView

        init {
            ButterKnife.bind(this, itemView)
        }

        fun setArticle(article: Article) {
            title.text = article.title
            date.text = article.pubDate.formatDate()
            newIndicator // TODO
            favouriteIndicator.setImageResource(
                if (article.isFavorite) R.drawable.ic_favorite
                else R.drawable.ic_not_favorite
            )
        }

        @OnClick(R.id.article_content_container)
        fun onArticleClick() {
            itemClickSubject.accept(data[adapterPosition])
        }

        @OnClick(R.id.article_favourite_indicator)
        fun onFeedFavouriteArticleClick() {
            favouriteClickSubject.accept(data[adapterPosition])
        }
    }
}