package me.atomx2u.rssr.domain.model


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
