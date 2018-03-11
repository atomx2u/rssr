package me.atomx2u.rssr.dagger

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.reactivex.schedulers.Schedulers
import me.atomx2u.rssr.data.DataModule
import me.atomx2u.rssr.data.RepositoryImpl
import me.atomx2u.rssr.data.database.DAOImpl
import me.atomx2u.rssr.data.pref.Prefs
import me.atomx2u.rssr.data.service.ServiceImpl
import me.atomx2u.rssr.data.util.TimeUtils
import me.atomx2u.rssr.data.util.TimeUtilsImpl
import me.atomx2u.rssr.domain.repository.Repository
import javax.inject.Inject

class App : Application(), HasActivityInjector {

    lateinit var prefs: Prefs
    lateinit var timeUtils: TimeUtils
    lateinit var repo: Repository
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.create().inject(this)

        DataModule.install(this)
        // context 需要在生命周期中调用，构造器中不能调用（还没有初始化好）
        prefs = Prefs(this)
        timeUtils = TimeUtilsImpl()
        repo = RepositoryImpl(
            DAOImpl(Schedulers.io(), Schedulers.single()),
            ServiceImpl(),
            prefs,
            timeUtils)
        instance = this
    }

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector

    companion object {
        var instance: App? = null
    }
}