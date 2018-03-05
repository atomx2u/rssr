package me.atomx2u.rssr.data.service

import com.einmalfel.earl.EarlParser
import com.einmalfel.earl.Feed
import io.reactivex.Single
import java.net.URL

class ServiceImpl: Service {
    override fun fetchFeed(link: String): Single<Feed> {
        return Single.fromCallable {
            URL(link).openConnection().getInputStream().use {
                EarlParser.parseOrThrow(it, 0)
            }
        }
    }
}