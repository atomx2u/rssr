package me.atomx2u.rssr.dagger.app.module

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.atomx2u.rssr.dagger.app.ReadScheduler
import me.atomx2u.rssr.dagger.app.WriteScheduler

@Module
class SchedulerModule {

    @Provides
    fun provideMainScheduler(): Scheduler = AndroidSchedulers.mainThread()

    @ReadScheduler
    @Provides
    fun provideReadScheduler(): Scheduler = Schedulers.io()

    @WriteScheduler
    @Provides
    fun provideWriteScheduler(): Scheduler = Schedulers.single()
}