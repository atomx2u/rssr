package me.atomx2u.rssr.domain.interactor.feed

import io.reactivex.Completable

interface FeedValidator {

    /**
     * @throws InvalidFeedLinkException
     * */
    fun validateFeed(link: String): Completable

    class InvalidFeedLinkException : Exception()
}