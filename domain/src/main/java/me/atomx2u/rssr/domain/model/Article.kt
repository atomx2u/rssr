package me.atomx2u.rssr.domain.model

/**
 * feed's article entity.
 * @see Feed
 * */
data class Article(
    val id: Long,
    val imageLink: String,
    val link: String,
    val title: String,
    val description: String,
    val pubDate: Long,
    val isRead: Boolean,
    val isFavorite: Boolean
)