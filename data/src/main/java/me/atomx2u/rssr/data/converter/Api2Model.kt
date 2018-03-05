package me.atomx2u.rssr.data.converter

import com.einmalfel.earl.Feed
import com.einmalfel.earl.Item
import me.atomx2u.rssr.data.database.model.ArticleModel
import me.atomx2u.rssr.data.database.model.FeedModel

fun Feed.toModel(
    feedLink: String,
    currentTime: Long
): Pair<FeedModel, List<ArticleModel>> {

    return FeedModel(
        imageLink = imageLink ?: "",
        feedLink = feedLink,
        pageLink = link ?: "",
        title = title,
        description = description ?: ""
    ) to items.map { item: Item -> with(item) {
        ArticleModel(
            imageLink = imageLink ?: "",
            link = link ?: "",
            title = title ?: "",
            description = description ?: "",
            pubDate = publicationDate?.time ?: currentTime
        )
    }}
}