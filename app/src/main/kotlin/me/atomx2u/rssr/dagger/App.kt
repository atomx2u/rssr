package me.atomx2u.rssr.dagger

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.reactivex.schedulers.Schedulers
import me.atomx2u.rssr.data.DataInitializer
import me.atomx2u.rssr.data.RepositoryImpl
import me.atomx2u.rssr.data.database.DAOImpl
import me.atomx2u.rssr.data.pref.Prefs
import me.atomx2u.rssr.data.service.ServiceImpl
import me.atomx2u.rssr.data.util.TimeUtils
import me.atomx2u.rssr.data.util.TimeUtilsImpl
import me.atomx2u.rssr.domain.repository.Repository
import javax.inject.Inject

class App : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    lateinit var prefs: Prefs
    lateinit var timeUtils: TimeUtils
    lateinit var repo: Repository

    override fun onCreate() {
        super.onCreate()
        DataInitializer.init(this)
        DaggerAppComponent
            .builder()
            .application(this)
            .build()
            .inject(this)

        prefs = Prefs(this)
        timeUtils = TimeUtilsImpl()
        repo = RepositoryImpl(
            DAOImpl(Schedulers.io(), Schedulers.single()),
            ServiceImpl(),
            prefs,
            timeUtils)
        instance = this
    }

    companion object {
        lateinit var instance: App
    }

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector
}