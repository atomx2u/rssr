package me.atomx2u.rss.data.database

import io.reactivex.Completable
import io.reactivex.Single
import me.atomx2u.rss.data.database.model.ArticleModel
import me.atomx2u.rss.data.database.model.FeedModel
import me.atomx2u.rss.domain.Article
import me.atomx2u.rss.domain.Feed

interface DAO {
    fun doesFeedExists(link: String): Single<Boolean>
    fun insertFeedAndArticles(feedModel: FeedModel, articleModels: List<ArticleModel>): Completable
    fun deleteFeedAndArticles(feedId: Long): Completable
    fun updateFeedAndArticles(feedId: Long, feedModel: FeedModel, articleModels: List<ArticleModel>): Completable
    fun getFeed(feedId: Long): Single<Feed>
    fun getAllFeeds(): Single<List<Feed>>

    fun getAllArticles(): Single<List<Article>>
    fun getArticles(feedId: Long): Single<List<Article>>
    fun markArticleAsRead(articleId: Long): Completable
}