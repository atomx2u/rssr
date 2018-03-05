package me.atomx2u.rssr.data.converter

import me.atomx2u.rssr.data.database.model.ArticleModel
import me.atomx2u.rssr.data.database.model.FeedModel
import me.atomx2u.rssr.domain.Article
import me.atomx2u.rssr.domain.Feed

fun FeedModel.toDomain(): Feed = Feed(_id, imageLink, feedLink, pageLink, title, description)
fun ArticleModel.toDomain(): Article = Article(_id, imageLink, link, title, description, pubDate, isRead)