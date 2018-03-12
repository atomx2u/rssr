package me.atomx2u.rssr.dagger.module

import dagger.Module
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Module
class SchedulerModule {

    fun provideMainScheduler(): Scheduler = AndroidSchedulers.mainThread()

    fun provideReadScheduler(): Scheduler = Schedulers.io()

    fun provideWriteScheduler(): Scheduler = Schedulers.single()
}