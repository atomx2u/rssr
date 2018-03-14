package me.atomx2u.rssr.dagger.main

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.atomx2u.rssr.dagger.FragmentScope
import me.atomx2u.rssr.dagger.main.frag.*
import me.atomx2u.rssr.ui.article.content.ArticleContentFragment
import me.atomx2u.rssr.ui.article.list.ArticlesFragment
import me.atomx2u.rssr.ui.feed.addition.AddFeedFragment
import me.atomx2u.rssr.ui.feed.subscription.UserSubscriptionFragment

@Module
interface MainFragmentBindingsModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [AddFeedModule::class])
    fun contributesAddFeedFragmentInjector(): AddFeedFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [UserSubscriptionModule::class, UserSubscriptionModule2::class])
    fun contributesUserSubscriptionFragmentInjector(): UserSubscriptionFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [ArticlesModule::class])
    fun contributesArticlesFragmentInjector(): ArticlesFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [ArticleContentModule::class])
    fun contributesArticleContentFragmentInjector(): ArticleContentFragment
}