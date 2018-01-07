package me.atomx2u.rss.data.database.definition

import com.raizlabs.android.dbflow.annotation.Database

@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
class AppDatabase {

    companion object {
        const val NAME = "AppDatabase"
        const val VERSION = 1
    }
}
