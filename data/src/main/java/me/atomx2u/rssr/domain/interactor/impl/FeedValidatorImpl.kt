package me.atomx2u.rssr.domain.interactor.impl

import io.reactivex.Completable
import me.atomx2u.rssr.domain.interactor.feed.FeedValidator

class FeedValidatorImpl : FeedValidator {
    override fun validateFeed(link: String): Completable {
        return Completable.fromAction {
//            http://rss.tgbus.com/psp_soft.xml
            if (!link.trim().startsWith("http://", ignoreCase = true)) {
                throw FeedValidator.InvalidFeedLinkException()
            }
        }
    }
}