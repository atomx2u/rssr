package me.atomx2u.rssr.data

import io.reactivex.Completable
import io.reactivex.Single
import me.atomx2u.rssr.data.converter.toModelPair
import me.atomx2u.rssr.data.database.DAO
import me.atomx2u.rssr.data.database.model.ArticleModel
import me.atomx2u.rssr.data.database.model.FeedModel
import me.atomx2u.rssr.data.pref.Prefs
import me.atomx2u.rssr.data.service.Service
import me.atomx2u.rssr.data.util.TimeUtils
import me.atomx2u.rssr.domain.model.Article
import me.atomx2u.rssr.domain.model.Feed
import me.atomx2u.rssr.domain.repository.Repository

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
                 feed.toModelPair(link, timeUtils.getCurrentTime())
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

    override fun setFavoriteToArticle(articleId: Long, isFavorite: Boolean): Completable {
        return dao.setFavoriteToArticle(articleId, isFavorite)
    }

    override fun getFavoriteArticles(): Single<List<Article>> {
        return dao.getFavoriteArticles()
    }

    override fun pullArticlesForFeedFromOrigin(feed: Feed): Completable {
        return service.fetchFeed(feed.feedLink).map { apiFeed: com.einmalfel.earl.Feed ->
            apiFeed.toModelPair(feed.feedLink, timeUtils.getCurrentTime())
        }.flatMapCompletable { (feedModel: FeedModel, articleModels: List<ArticleModel>) ->
            dao.updateFeedAndArticles(feed.id, feedModel, articleModels)
        }
//        dao.getFeed(feedId).flatMap { feed ->
//            service.fetchFeed(feed.feedLink).zipWith(Single.just(feed.feedLink))
//        }.map { (feed: com.einmalfel.earl.Feed, feedLink: String) ->
//            feed.toModelPair(feedLink, timeUtils.getCurrentTime())
//        }.flatMapCompletable { (feedModel: FeedModel, articleModels: List<ArticleModel>) ->
//            dao.insertFeedAndArticles(feedModel, articleModels)
//        }
    }

    override fun shouldUpdateFeedsInBackground(): Single<Boolean> {
        return Single.fromCallable { prefs.shouldUpdateFeedsInBackground() }
    }

    override fun setShouldUpdateFeedsInBackground(shouldUpdate: Boolean): Completable {
        return Single.fromCallable {
            if (!prefs.setShouldUpdateFeedsInBackground(shouldUpdate))
                throw Exception("invoke setAutoFeedsUpdate(isEnabled = $shouldUpdate) fail.")
        }.toCompletable()
    }
}