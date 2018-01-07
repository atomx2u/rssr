package me.atomx2u.rss.data.database.model

import com.raizlabs.android.dbflow.annotation.*
import com.raizlabs.android.dbflow.structure.BaseModel
import me.atomx2u.rss.data.database.definition.AppDatabase

// todo: for now, in kotlin, a annotation cannot be used as annotations argument.
@Table(database = AppDatabase::class)
//    uniqueColumnGroups = [@UniqueGroup(groupNumber = UNIQUE_GROUP_ID, uniqueConflict = ConflictAction.IGNORE)])
class FeedModel(

    @PrimaryKey(autoincrement = true) var
    _id: Long = 0,

    @Column var
    imageLink: String = "",

    @Column @Unique(onUniqueConflict = ConflictAction.FAIL) var
    feedLink: String = "",

    @Column var
    pageLink: String = "",

    @Column var
    title: String = "",

    @Column var
    description: String = ""

): BaseModel()
//{
//    companion object {
//        const val UNIQUE_GROUP_ID = 1
//    }
//}