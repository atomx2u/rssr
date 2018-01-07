package me.atomx2u.rss.domain.component

import io.reactivex.Completable

interface FeedValidator {

    /**
     * @throws InvalidFeedLinkException
     * */
    fun validateFeed(link: String): Completable

    class InvalidFeedLinkException : Exception()
}