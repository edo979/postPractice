package com.edo979.data_local.db.post

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post")
data class PostEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "body") val body: String,
    @ColumnInfo(name = "user_id") val userId: Long,
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "email") val authorEmail: String
)