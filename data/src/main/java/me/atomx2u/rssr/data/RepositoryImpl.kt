package me.atomx2u.rssr.data

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.rxkotlin.zipWith
import me.atomx2u.rssr.data.converter.toModel
import me.atomx2u.rssr.data.database.DAO
import me.atomx2u.rssr.data.database.model.ArticleModel
import me.atomx2u.rssr.data.database.model.FeedModel
import me.atomx2u.rssr.data.pref.Prefs
import me.atomx2u.rssr.data.service.Service
import me.atomx2u.rssr.data.util.TimeUtils
import me.atomx2u.rssr.domain.model.Article
import me.atomx2u.rssr.domain.model.Feed
import me.atomx2u.rssr.domain.Repository

// todo 自动更新 feed
// 校验形的错误应该给出 UI 上的提示
class RepositoryImpl(
    private val dao: DAO,
    private val service: Service,
    private val prefs: Prefs,
    private val timeUtils: TimeUtils
) : Repository {

    override fun doesFeedExists(link: String): Single<Boolean> {
        return dao.doesFeedExists(link)
    }

    override fun insertFeed(link: String): Completable {
         return service.fetchFeed(link)
             .map { feed: com.einmalfel.earl.Feed ->
                 feed.toModel(link, timeUtils.getCurrentTime())
             }.flatMapCompletable { (feedModel: FeedModel, articleModels: List<ArticleModel>) ->
                 dao.insertFeedAndArticles(feedModel, articleModels)
             }
    }

    override fun deleteFeed(feedId: Long): Completable {
        return dao.deleteFeedAndArticles(feedId)
    }

    override fun getSubscribedFeeds(): Single<List<Feed>> {
        return dao.getAllFeeds()
    }

    override fun getArticles(feedId: Long): Single<List<Article>> {
        return dao.getArticles(feedId)
    }

    override fun markArticleAsRead(articleId: Long): Completable {
        return dao.markArticleAsRead(articleId)
    }

    override fun isAutoFeedsUpdateEnabled(): Single<Boolean> {
        return Single.fromCallable { prefs.getIsAutoFeedsUpdateEnabled() }
    }

    override fun setAutoFeedsUpdate(isEnabled: Boolean): Completable {
        return Single.fromCallable {
            if (!prefs.setIsAutoFeedsUpdateEnabled(isEnabled))
                throw Exception("invoke setAutoFeedsUpdate(isEnabled = $isEnabled) fail.")
        }.toCompletable()
    }

    override fun updateFeed(feedId: Long): Completable {
        return dao.getFeed(feedId).flatMap { feed ->
            service.fetchFeed(feed.feedLink).zipWith(Single.just(feed.feedLink))
        }.map { (feed: com.einmalfel.earl.Feed, feedLink: String) ->
            feed.toModel(feedLink, timeUtils.getCurrentTime())
        }.flatMapCompletable { (feedModel: FeedModel, articleModels: List<ArticleModel>) ->
            dao.insertFeedAndArticles(feedModel, articleModels)
        }
    }

    override fun setFavoriteToArticle(articleId: Long, isFavorite: Boolean): Completable {
        return dao.setFavoriteToArticle(articleId, isFavorite)
    }

    override fun getFavoriteArticles(): Single<List<Article>> {
        return dao.getFavoriteArticles()
    }
}