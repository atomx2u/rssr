package me.atomx2u.rssr.dagger

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import me.atomx2u.rssr.dagger.module.MainActivityInstallModule
import me.atomx2u.rssr.dagger.module.SchedulerModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    MainActivityInstallModule::class,
    SchedulerModule::class
])
interface AppComponent {

    fun inject(app: App)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(@AppContext context: Application): Builder

        fun build(): AppComponent
    }
}