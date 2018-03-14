package me.atomx2u.rssr.dagger.app.module

import dagger.Module
import dagger.Provides
import me.atomx2u.rssr.domain.interactor.article.GetArticlesUseCase
import me.atomx2u.rssr.domain.interactor.article.favorite.FavoriteArticleUseCase
import me.atomx2u.rssr.domain.interactor.article.favorite.UnFavoriteArticleUseCase
import me.atomx2u.rssr.domain.interactor.feed.AddFeedUseCase
import me.atomx2u.rssr.domain.interactor.feed.DeleteFeedUseCase
import me.atomx2u.rssr.domain.interactor.feed.GetSubscribedFeedsUseCase
import me.atomx2u.rssr.domain.interactor.feed.IsFeedSubscribedUseCase
import me.atomx2u.rssr.domain.repository.Repository
import javax.inject.Singleton


@Module
class UseCaseModule {
    @Singleton
    @Provides
    fun provideAddFeedUseCase(repository: Repository) = AddFeedUseCase(repository)

    @Singleton
    @Provides
    fun provideDeleteFeedUseCase(repository: Repository) = DeleteFeedUseCase(repository)

    @Singleton
    @Provides
    fun provideIsFeedSubscribedUseCase(repository: Repository) = IsFeedSubscribedUseCase(repository)

    @Singleton
    @Provides
    fun provideGetSubscribedFeedsUseCase(repository: Repository) = GetSubscribedFeedsUseCase(repository)

    @Singleton
    @Provides
    fun provideGetArticlesUseCase(repository: Repository) = GetArticlesUseCase(repository)

    @Singleton
    @Provides
    fun provideFavoriteArticleUseCase(repository: Repository) = FavoriteArticleUseCase(repository)

    @Singleton
    @Provides
    fun provideUnFavoriteArticleUseCase(repository: Repository) = UnFavoriteArticleUseCase(repository)
}