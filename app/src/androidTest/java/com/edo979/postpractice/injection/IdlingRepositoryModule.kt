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
import com.edo979.postpractice.idling.ComposeCountingIdlingResource
import com.edo979.postpractice.repository.IdlingLocalPostRepository
import com.edo979.postpractice.repository.IdlingRemotePostRepository
import com.edo979.postpractice.repository.IdlingRemoteUserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton


@Module
@TestInstallIn(
    components = [SingletonComponent::class], replaces = [RepositoryModule::class]
)
class IdlingRepositoryModule {

    @Singleton
    @Provides
    fun provideIdlingResource(): ComposeCountingIdlingResource =
        ComposeCountingIdlingResource("repository-idling")

    @Provides
    fun providePostRepository(
        countingIdlingResource: ComposeCountingIdlingResource,
        remotePostDataSource: RemotePostDataSource
    ): PostRepository =
        IdlingRemotePostRepository(PostRepositoryImpl(remotePostDataSource), countingIdlingResource)

    @Provides
    fun provideUserRepository(
        countingIdlingResource: ComposeCountingIdlingResource,
        remoteUserDataSource: RemoteUserDataSource
    ): UserRepository =
        IdlingRemoteUserRepository(UserRepositoryImpl(remoteUserDataSource), countingIdlingResource)

    @Provides
    fun provideFavoriteRepository(
        countingIdlingResource: ComposeCountingIdlingResource,
        localPostDataSource: LocalPostDataSource
    ): FavoritePostRepository =
        IdlingLocalPostRepository(
            FavoritePostRepositoryImpl(localPostDataSource),
            countingIdlingResource
        )
}