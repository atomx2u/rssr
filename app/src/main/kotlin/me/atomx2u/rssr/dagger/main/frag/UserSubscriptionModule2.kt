package me.atomx2u.rssr.dagger.main.frag

import dagger.Module
import dagger.Provides
import me.atomx2u.rssr.dagger.FragmentScope
import me.atomx2u.rssr.dagger.main.ForFragment
import me.atomx2u.rssr.ui.feed.subscription.FeedAdapter
import me.atomx2u.rssr.ui.feed.subscription.UserSubscriptionFragment
import me.atomx2u.rssr.util.FragmentContext
import me.atomx2u.rssr.util.ImageLoader
import me.atomx2u.rssr.util.ImageLoaderImpl

@Module
class UserSubscriptionModule2 {

    @ForFragment
    @FragmentScope
    @Provides
    fun provideImageLoader(fragment: UserSubscriptionFragment): ImageLoader = ImageLoaderImpl(FragmentContext(fragment))

    @FragmentScope
    @Provides
    fun provideFeedAdapter(@ForFragment imageLoader: ImageLoader) = FeedAdapter(imageLoader)
}