package me.atomx2u.rss.data

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.rxkotlin.zipWith
import me.atomx2u.rss.data.converter.toModel
import me.atomx2u.rss.data.database.DAO
import me.atomx2u.rss.data.database.model.ArticleModel
import me.atomx2u.rss.data.database.model.FeedModel
import me.atomx2u.rss.data.preference.Prefs
import me.atomx2u.rss.data.service.Service
import me.atomx2u.rss.data.util.TimeUtils
import me.atomx2u.rss.domain.Article
import me.atomx2u.rss.domain.Feed
import me.atomx2u.rss.domain.Repository

// todo 感知网络状态
class RepositoryImpl(
    private val dao: DAO,
    private val service: Service,
    private val prefs: Prefs,
    private val timeUtils: TimeUtils
) : Repository {

    override fun doesFeedExists(link: String): Single<Boolean> {
        return dao.doesFeedExists(link)
    }

    // todo: 包可见，是不是在其他包中调用此包的闭包的时候也是可见的？
    // todo: doOnError 后 subscribeTo 还能接受到 onError 吗? 如果不能，交互将不能完成（这是有问题的）。
    // # 应该 ui 上知道失败吗？
    // $ 应该的。
    // # 有必要知道为什么知道吗？
    // $ 不应该，App 应该是一个黑盒，用户没必要知道。
    // # 是不是所有的错误都不需要详细的提示？
    // $ 不是的，校验型的应该给出具体的提示。
    override fun insertFeed(feedLink: String): Completable {
         return service.fetchFeed(feedLink)
             .map { feed: com.einmalfel.earl.Feed ->
                 feed.toModel(feedLink, timeUtils.getCurrentTime())
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
}