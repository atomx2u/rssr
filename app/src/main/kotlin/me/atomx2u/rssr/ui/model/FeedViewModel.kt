package me.atomx2u.rssr.ui.model

import me.atomx2u.rssr.domain.Feed

data class FeedViewModel(
    val id: Long,
    val imageLink: String,
    val feedLink: String,
    val pageLink: String,
    val title: String,
    val description: String,
    val isSelected: Boolean = false
)
fun Feed.toViewModel() = FeedViewModel(id, imageLink, feedLink, pageLink, title, description)