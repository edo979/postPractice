package com.edo979.postpractice.injection

import com.edo979.domain.repository.FavoritePostRepository
import com.edo979.domain.repository.PostRepository
import com.edo979.domain.repository.UserRepository
import com.edo979.domain.usecase.GetPostUseCase
import com.edo979.domain.usecase.GetPostsWithUsersUseCase
import com.edo979.domain.usecase.UseCase
import com.edo979.domain.usecase.favoritePost.DeleteFavoritePostUseCase
import com.edo979.domain.usecase.favoritePost.GetFavoritePostsWithUsersUseCase
import com.edo979.domain.usecase.favoritePost.SaveFavoritePostUseCase
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
        userRepository: UserRepository,
        favoritePostRepository: FavoritePostRepository
    ): GetPostsWithUsersUseCase =
        GetPostsWithUsersUseCase(
            configuration,
            postRepository,
            userRepository,
            favoritePostRepository
        )

    @Provides
    fun provideGetSinglePostWithUser(
        configuration: UseCase.Configuration,
        postRepository: PostRepository,
        favoritePostRepository: FavoritePostRepository,
        userRepository: UserRepository
    ): GetPostUseCase =
        GetPostUseCase(configuration, postRepository, favoritePostRepository, userRepository)

//    @Provides
//    fun provideGetFavoritePostUseCase(
//        configuration: UseCase.Configuration,
//        favoritePostRepository: FavoritePostRepository
//    ): GetFavoritePostsWithUsersUseCase =
//        GetFavoritePostsWithUsersUseCase(configuration, favoritePostRepository)

    @Provides
    fun getSaveFavoritePostUseCase(
        configuration: UseCase.Configuration,
        favoritePostRepository: FavoritePostRepository
    ): SaveFavoritePostUseCase =
        SaveFavoritePostUseCase(configuration, favoritePostRepository)

    @Provides
    fun provideDeleteFavoritePostUseCase(
        configuration: UseCase.Configuration,
        favoritePostRepository: FavoritePostRepository
    ): DeleteFavoritePostUseCase =
        DeleteFavoritePostUseCase(configuration, favoritePostRepository)
}