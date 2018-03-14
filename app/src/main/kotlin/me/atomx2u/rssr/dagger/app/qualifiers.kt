package me.atomx2u.rssr.dagger.app

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class AppContext

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ReadScheduler

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class WriteScheduler