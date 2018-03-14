package me.atomx2u.rssr.dagger.app.module

import android.content.Context
import dagger.Binds
import dagger.Module
import me.atomx2u.rssr.dagger.app.App
import me.atomx2u.rssr.dagger.app.AppContext

@Module
interface AppModule {
    @AppContext
    @Binds
    fun bindAppContext(app: App): Context
}