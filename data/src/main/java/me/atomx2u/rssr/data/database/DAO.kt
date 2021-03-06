package me.atomx2u.rssr.data.database

import io.reactivex.Completable
import io.reactivex.Single
import me.atomx2u.rssr.data.database.model.ArticleModel
import me.atomx2u.rssr.data.database.model.FeedModel
import me.atomx2u.rssr.domain.model.Article
import me.atomx2u.rssr.domain.model.Feed

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

    fun setFavoriteToArticle(articleId: Long, isFavorite: Boolean): Completable
    fun getFavoriteArticles(): Single<List<Article>>
}