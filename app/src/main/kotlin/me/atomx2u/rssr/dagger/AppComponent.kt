package me.atomx2u.rssr.dagger

import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AModule::class
])
interface AppComponent {
    fun inject(app: App)
}