package com.edo979.presentation_post.list

import com.edo979.domain.usecase.GetPostsWithUsersUseCase
import com.edo979.presentation_common.state.CommonConverter

class PostListConverter : CommonConverter<GetPostsWithUsersUseCase.Response, PostListModel>() {

    override fun convertSuccess(data: GetPostsWithUsersUseCase.Response): PostListModel =
        PostListModel(items = data.posts.map {
            PostListItemModel(
                id = it.post.id,
                userId = it.post.userId,
                author = it.user.name,
                title = it.post.title
            )
        })
}