package me.atomx2u.rss.dagger

import android.app.Application
import me.atomx2u.rss.data.RepositoryImpl
import me.atomx2u.rss.data.database.DAOImpl
import me.atomx2u.rss.data.preference.Prefs
import me.atomx2u.rss.data.service.ServiceImpl
import me.atomx2u.rss.data.util.TimeUtils
import me.atomx2u.rss.data.util.TimeUtilsImpl
import me.atomx2u.rss.domain.Repository

class App : Application() {

    lateinit var prefs: Prefs
    lateinit var timeUtils: TimeUtils
    lateinit var repo: Repository

    override fun onCreate() {
        super.onCreate()
        com.raizlabs.android.dbflow.config.FlowManager.init(this)
        // context 需要在生命周期中调用，构造器中不能调用（还没有初始化好）
        prefs = Prefs(this)
        timeUtils = TimeUtilsImpl()
        repo = RepositoryImpl(DAOImpl(), ServiceImpl(), prefs, timeUtils)
    }


}