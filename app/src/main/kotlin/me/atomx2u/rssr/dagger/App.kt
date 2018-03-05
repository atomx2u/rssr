package me.atomx2u.rssr.dagger

import android.app.Application
import me.atomx2u.rssr.data.DataModule
import me.atomx2u.rssr.data.RepositoryImpl
import me.atomx2u.rssr.data.database.DAOImpl
import me.atomx2u.rssr.data.preference.Prefs
import me.atomx2u.rssr.data.service.ServiceImpl
import me.atomx2u.rssr.data.util.TimeUtils
import me.atomx2u.rssr.data.util.TimeUtilsImpl
import me.atomx2u.rssr.domain.Repository

class App : Application() {

    lateinit var prefs: Prefs
    lateinit var timeUtils: TimeUtils
    lateinit var repo: Repository

    override fun onCreate() {
        super.onCreate()
        DataModule.install(this)
        // context 需要在生命周期中调用，构造器中不能调用（还没有初始化好）
        prefs = Prefs(this)
        timeUtils = TimeUtilsImpl()
        repo = RepositoryImpl(DAOImpl(), ServiceImpl(), prefs, timeUtils)
    }
}