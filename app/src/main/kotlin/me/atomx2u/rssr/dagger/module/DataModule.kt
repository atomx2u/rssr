package me.atomx2u.rssr.dagger.module

import android.content.Context
import dagger.Module
import io.reactivex.Scheduler
import me.atomx2u.rssr.dagger.AppContext
import me.atomx2u.rssr.dagger.ReadScheduler
import me.atomx2u.rssr.dagger.WriteScheduler
import me.atomx2u.rssr.data.RepositoryImpl
import me.atomx2u.rssr.data.database.DAO
import me.atomx2u.rssr.data.database.DAOImpl
import me.atomx2u.rssr.data.pref.Prefs
import me.atomx2u.rssr.data.service.Service
import me.atomx2u.rssr.data.service.ServiceImpl
import me.atomx2u.rssr.data.util.TimeUtils
import me.atomx2u.rssr.data.util.TimeUtilsImpl
import me.atomx2u.rssr.domain.repository.Repository
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    fun providePrefs(@AppContext context: Context): Prefs = Prefs(context)

    @Singleton
    fun provideDao(
        @ReadScheduler readScheduler: Scheduler,
        @WriteScheduler writeScheduler: Scheduler
    ): DAO = DAOImpl(readScheduler, writeScheduler)

    @Singleton
    fun provideService(): Service = ServiceImpl()

    @Singleton
    fun provideTimeUtils(): TimeUtils = TimeUtilsImpl()

    @Singleton
    fun provideReposity(dao: DAO, prefs: Prefs, service: Service, timeUtils: TimeUtils): Repository =
        RepositoryImpl(dao, service, prefs, timeUtils)
}