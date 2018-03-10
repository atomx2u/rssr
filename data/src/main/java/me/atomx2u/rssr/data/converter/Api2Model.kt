package me.atomx2u.rssr.data.converter

import me.atomx2u.rssr.data.database.model.ArticleModel
import me.atomx2u.rssr.data.database.model.FeedModel

internal fun com.einmalfel.earl.Feed.toModelPair(
    feedLink: String,
    currentTime: Long
): Pair<FeedModel, List<ArticleModel>> {

    return FeedModel(
        imageLink = imageLink ?: "",
        feedLink = feedLink,
        pageLink = link ?: "",
        title = title,
        description = description ?: ""
    ) to items.map { item: com.einmalfel.earl.Item -> with(item) {
        ArticleModel(
            imageLink = imageLink ?: "",
            link = link ?: "",
            title = title ?: "",
            description = description ?: "",
            pubDate = publicationDate?.time ?: currentTime
        )
    }}
}

internal fun com.einmalfel.earl.Feed.toArticleModels(
    currentTime: Long
): List<ArticleModel> {
    return items.map { item: com.einmalfel.earl.Item -> with(item) {
        ArticleModel(
            imageLink = imageLink ?: "",
            link = link ?: "",
            title = title ?: "",
            description = description ?: "",
            pubDate = publicationDate?.time ?: currentTime
        )
    }}
}