package me.atomx2u.rssr.data.database

import com.raizlabs.android.dbflow.config.FlowManager
import com.raizlabs.android.dbflow.kotlinextensions.*
import com.raizlabs.android.dbflow.rx2.kotlinextensions.list
import com.raizlabs.android.dbflow.rx2.kotlinextensions.result
import com.raizlabs.android.dbflow.rx2.kotlinextensions.rx
import io.reactivex.Completable
import io.reactivex.Single
import me.atomx2u.rssr.data.converter.toDomain
import me.atomx2u.rssr.data.database.definition.AppDatabase
import me.atomx2u.rssr.data.database.model.ArticleModel
import me.atomx2u.rssr.data.database.model.ArticleModel_Table
import me.atomx2u.rssr.data.database.model.FeedModel
import me.atomx2u.rssr.data.database.model.FeedModel_Table
import me.atomx2u.rssr.domain.Article
import me.atomx2u.rssr.domain.Feed

class DAOImpl() : DAO {

    override fun doesFeedExists(link: String): Single<Boolean> {
        return (select from FeedModel::class where (FeedModel_Table.feedLink `is` link))
            .rx().result
            .isEmpty
            .map { !it }
    }

    // BaseRXModel 不支持多个表的事务。
    override fun insertFeedAndArticles(feedModel: FeedModel, articleModels: List<ArticleModel>): Completable {
        return Completable.create { emitter ->
            FlowManager.getDatabase(AppDatabase::class.java)
                .beginTransactionAsync { databaseWrapper ->
                    val feedId = feedModel.insert(databaseWrapper)
                    articleModels.forEach { articleModel ->
                        articleModel.feedId = feedId
                        articleModel.insert()
                    }
                }
                .success { emitter.onComplete() }
                .error { _, error -> emitter.onError(error) }
                .execute()
        }
    }

    // $ 使用外键后，删除已经要先删除外键引用。
    override fun deleteFeedAndArticles(feedId: Long): Completable {
        return Completable.create { emitter ->
            FlowManager.getDatabase(AppDatabase::class.java)
                .beginTransactionAsync { databaseWrapper ->
                    innerGetArticles(feedId).result?.delete(databaseWrapper)?.let { isSuccessful ->
                        if (!isSuccessful) {
                            throw Exception("invoke deleteFeedAndArticles(feedId = $feedId), delete articleModels fail.")
                        }
                    }
                    innerGetFeed(feedId).result!!.delete().let { isSuccessful ->
                        if (!isSuccessful) {
                            throw Exception("invoke deleteFeedAndArticles(feedId = $feedId), delete feedModel fail.")
                        }
                    }
                }
                .success { emitter.onComplete() }
                .error { _, error -> emitter.onError(error) }
                .execute()
        }
    }

    override fun getFeed(feedId: Long): Single<Feed> {
        return Single.fromCallable {
            innerGetFeed(feedId).result!!.toDomain()
        }
    }

    // # model to domain 的开销还是挺大的，能减小这种开销吗？
    // $ 目前没发现办法。但是这个开销应该可以可以接受的。
    override fun getAllFeeds(): Single<List<me.atomx2u.rssr.domain.Feed>> {
        return (select from FeedModel::class)
            .rx().list
            .map { feedModels -> feedModels.map { it.toDomain() } }
    }

    override fun updateFeedAndArticles(feedId: Long, feedModel: FeedModel, articleModels: List<ArticleModel>): Completable {
        return Completable.create { emitter ->
            FlowManager.getDatabase(AppDatabase::class.java)
                .beginTransactionAsync { databaseWrapper ->
                    feedModel.apply { _id = feedId }.update().let { isSuccessful ->
                        if (!isSuccessful) {
                            throw Exception("invoke updateFeedAndArticles(feedId = $feedId, feedModel: $feedModel, articleModels: $articleModels), update feedModel fail.")
                        }
                    }
                    articleModels.forEach { articleModel ->
                        articleModel.apply { this.feedId = feedId }.update().let { isSuccessful ->
                            if (!isSuccessful) {
                                throw Exception("invoke updateFeedAndArticles(feedId = $feedId, feedModel: $feedModel, articleModels: $articleModels), update articleModel fail.")
                            }
                        }
                    }
                }
                .success { emitter.onComplete() }
                .error { _, error -> emitter.onError(error) }
                .execute()
        }
    }

    override fun getAllArticles(): Single<List<Article>> {
        return innerGetAllArticles().rx().list.map { articleModels ->
            articleModels.map { articleModel -> articleModel.toDomain() }
        }
    }

    override fun getArticles(feedId: Long): Single<List<Article>> {
        return innerGetArticles(feedId).rx().list.map { articleModels ->
            articleModels.map { articleModel -> articleModel.toDomain() }
        }
    }

    override fun markArticleAsRead(articleId: Long): Completable {
        return Completable.fromCallable {
            innerGetArticle(articleId).result!!.let { articleModel ->
                articleModel.apply { isRead = true }.update().let { isSuccessful ->
                    if (!isSuccessful) {
                        throw Exception("invoke markArticleAsRead(articleId = $articleId), update articleModel fail.")
                    }
                }
            }
        }
    }

    private fun innerGetAllArticles() = (select from ArticleModel::class)

    private fun innerGetFeed(feedId: Long) =
        (select from FeedModel::class where (FeedModel_Table._id `is` feedId) limit 1)

    private fun innerGetArticles(feedId: Long) =
        (select from ArticleModel::class where (ArticleModel_Table.feedId__id `is` feedId))

    private fun innerGetArticle(articleId: Long) =
        (select from ArticleModel::class where (ArticleModel_Table._id `is` articleId) limit 1)
}
