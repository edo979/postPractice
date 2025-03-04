package com.edo979.data_local.db.post

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {

    @Query("SELECT * FROM post")
    fun getPosts(): Flow<List<PostEntity>>

    @Query("SELECT * FROM post WHERE id = :id")
    suspend fun getPost(id: Long): PostEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPost(post: PostEntity)

    @Delete
    suspend fun deletePost(post: PostEntity)
}