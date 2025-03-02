package com.edo979.data_remote.injection

import com.edo979.data_remote.source.MockRemotePostDataSource
import com.edo979.data_remote.source.MockRemoteUserDataSource
import com.edo979.data_repository.data_source.remote.RemotePostDataSource
import com.edo979.data_repository.data_source.remote.RemoteUserDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {

    @Binds
    abstract fun bindPostDataSource(postDataSourceImpl: MockRemotePostDataSource): RemotePostDataSource

    @Binds
    abstract fun bindUserDataSource(postDataSourceImpl: MockRemoteUserDataSource): RemoteUserDataSource
}