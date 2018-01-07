package me.atomx2u.rss.domain

import io.reactivex.Completable
import io.reactivex.Single

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
}