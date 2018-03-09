package me.atomx2u.rssr.data.database.model

import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.ForeignKey
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel
import me.atomx2u.rssr.data.database.definition.AppDatabase

@Table(database = AppDatabase::class)
class ArticleModel(

    @PrimaryKey(autoincrement = true) var
    _id: Long = 0,

    @ForeignKey(tableClass = FeedModel::class) var
    feedId: Long? = null,

    @Column var
    imageLink: String = "",

    @Column var
    link: String = "",

    @Column var
    title: String = "",

    @Column var
    description: String = "",

    @Column var
    pubDate: Long = 0,

    @Column var
    isRead: Boolean = false,

    @Column var
    isFavorite: Boolean = false

) : BaseModel()