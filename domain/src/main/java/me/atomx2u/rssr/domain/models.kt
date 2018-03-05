package me.atomx2u.rssr.domain


/**
 * feed entity.
 * @See Article
 * */
data class Feed(
    val id: Long,
    val imageLink: String,
    val feedLink: String,
    val pageLink: String,
    val title: String,
    val description: String
)

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
    val isRead: Boolean
)