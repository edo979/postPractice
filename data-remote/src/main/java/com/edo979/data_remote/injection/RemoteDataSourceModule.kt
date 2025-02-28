package com.edo979.data_remote.injection

import com.edo979.data_remote.source.MockPostDataSource
import com.edo979.data_remote.source.MockUserDataSource
import com.edo979.data_repository.data_source.PostDataSource
import com.edo979.data_repository.data_source.UserDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {

    @Binds
    abstract fun bindPostDataSource(postDataSourceImpl: MockPostDataSource): PostDataSource

    @Binds
    abstract fun bindUserDataSource(postDataSourceImpl: MockUserDataSource): UserDataSource
}