package me.atomx2u.rssr.domain

import io.reactivex.Completable
import io.reactivex.Single
import me.atomx2u.rssr.domain.model.Article
import me.atomx2u.rssr.domain.model.Feed

interface Repository {
    fun doesFeedExists(link: String): Single<Boolean>
    fun insertFeed(link: String): Completable
    fun deleteFeed(feedId: Long): Completable
    fun getSubscribedFeeds(): Single<List<Feed>>

    fun getArticles(feedId: Long): Single<List<Article>>
    fun markArticleAsRead(articleId: Long): Completable

    fun isAutoFeedsUpdateEnabled(): Single<Boolean>
    fun setAutoFeedsUpdate(isEnabled: Boolean): Completable
    fun updateFeed(feedId: Long): Completable

    fun setFavoriteToArticle(articleId: Long, isFavorite: Boolean): Completable
    fun getFavoriteArticles(): Single<List<Article>>
}