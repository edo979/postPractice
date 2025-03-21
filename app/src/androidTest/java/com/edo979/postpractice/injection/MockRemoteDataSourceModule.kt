package com.edo979.postpractice.injection


import com.edo979.data_remote.injection.RemoteDataSourceModule
import com.edo979.data_repository.data_source.remote.RemotePostDataSource
import com.edo979.data_repository.data_source.remote.RemoteUserDataSource
import com.edo979.postpractice.remote.MockRemotePostDataSource
import com.edo979.postpractice.remote.MockRemoteUserDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class], replaces = [RemoteDataSourceModule::class]
)
abstract class MockRemoteDataSourceModule {

    @Binds
    abstract fun bindPostDataSource(
        postDataSourceImpl: MockRemotePostDataSource
    ): RemotePostDataSource

    @Binds
    abstract fun bindUserDataSource(
        userDataSourceImpl: MockRemoteUserDataSource
    ): RemoteUserDataSource
}