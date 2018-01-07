package me.atomx2u.rss.data.converter

import me.atomx2u.rss.data.database.model.ArticleModel
import me.atomx2u.rss.data.database.model.FeedModel
import me.atomx2u.rss.domain.Article
import me.atomx2u.rss.domain.Feed

fun FeedModel.toDomain(): Feed = Feed(_id, imageLink, feedLink, pageLink, title, description)
fun ArticleModel.toDomain(): Article = Article(_id, imageLink, link, title, description, pubDate, isRead)