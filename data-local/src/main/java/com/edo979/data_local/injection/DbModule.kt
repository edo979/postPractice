package com.edo979.data_local.injection

import android.content.Context
import androidx.room.Room
import com.edo979.data_local.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DbModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "my-database").build()

    @Provides
    fun providePostDao(appDatabase: AppDatabase) = appDatabase.postDao()
}