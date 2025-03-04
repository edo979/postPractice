package com.edo979.data_local.db

import androidx.room.Database
import com.edo979.data_local.db.post.PostDao
import com.edo979.data_local.db.post.PostEntity

@Database(entities = [PostEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase {

    abstract fun postDao(): PostDao
}