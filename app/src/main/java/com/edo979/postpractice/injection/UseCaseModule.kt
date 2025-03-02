package com.edo979.postpractice.injection

import com.edo979.domain.entity.UseCase
import com.edo979.domain.repository.PostRepository
import com.edo979.domain.repository.UserRepository
import com.edo979.domain.usecase.GetPostsWithUsersUseCase
import com.edo979.domain.usecase.GetPostWithUserWithIsFavoriteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers


@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun provideUseCaseConfiguration() = UseCase.Configuration(dispatcher = Dispatchers.IO)

    @Provides
    fun provideGetPostsWithUsersUseCase(
        configuration: UseCase.Configuration,
        postRepository: PostRepository,
        userRepository: UserRepository
    ): GetPostsWithUsersUseCase =
        GetPostsWithUsersUseCase(configuration, postRepository, userRepository)

    @Provides
    fun provideGetSinglePostWithUser(
        configuration: UseCase.Configuration,
        postRepository: PostRepository,
        userRepository: UserRepository
    ): GetPostWithUserWithIsFavoriteUseCase =
        GetPostWithUserWithIsFavoriteUseCase(configuration, postRepository, userRepository)
}