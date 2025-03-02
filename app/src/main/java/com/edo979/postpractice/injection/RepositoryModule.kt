package com.edo979.postpractice.injection

import com.edo979.data_remote.source.MockRemotePostDataSource
import com.edo979.data_remote.source.MockRemoteUserDataSource
import com.edo979.data_repository.repository.PostRepositoryImpl
import com.edo979.data_repository.repository.UserRepositoryImpl
import com.edo979.domain.repository.PostRepository
import com.edo979.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun providePostRepository(dataSource: MockRemotePostDataSource): PostRepository =
        PostRepositoryImpl(dataSource)

    @Provides
    fun provideUserRepository(dataSource: MockRemoteUserDataSource): UserRepository =
        UserRepositoryImpl(dataSource)
}