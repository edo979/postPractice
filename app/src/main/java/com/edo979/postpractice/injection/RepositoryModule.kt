package com.edo979.postpractice.injection

import com.edo979.data_repository.data_source.local.LocalPostDataSource
import com.edo979.data_repository.data_source.remote.RemotePostDataSource
import com.edo979.data_repository.data_source.remote.RemoteUserDataSource
import com.edo979.data_repository.repository.FavoritePostRepositoryImpl
import com.edo979.data_repository.repository.PostRepositoryImpl
import com.edo979.data_repository.repository.UserRepositoryImpl
import com.edo979.domain.repository.FavoritePostRepository
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
    fun providePostRepository(postDataSource: RemotePostDataSource): PostRepository =
        PostRepositoryImpl(postDataSource)

    @Provides
    fun provideUserRepository(userDataSource: RemoteUserDataSource): UserRepository =
        UserRepositoryImpl(userDataSource)

    @Provides
    fun provideFavoriteRepository(localDataSource: LocalPostDataSource): FavoritePostRepository =
        FavoritePostRepositoryImpl(localDataSource)
}