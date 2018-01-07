package me.atomx2u.rss.data.service

import com.einmalfel.earl.Feed
import io.reactivex.Single

interface Service {
    fun fetchFeed(link: String): Single<Feed>
}