package me.atomx2u.rssr.dagger.app

import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import me.atomx2u.rssr.dagger.app.module.*
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AndroidSupportInjectionModule::class,
    AppModule::class,
    UseCaseModule::class,
    DataModule::class,
    SchedulerModule::class,
    ActivityBindingsModule::class
])
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()
}