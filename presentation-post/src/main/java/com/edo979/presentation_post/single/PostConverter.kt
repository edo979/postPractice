package com.edo979.presentation_post.single

import com.edo979.domain.usecase.GetPostUseCase
import com.edo979.presentation_common.state.CommonConverter
import javax.inject.Inject

class PostConverter @Inject constructor() :
    CommonConverter<GetPostUseCase.Response, PostModel>() {

    override fun convertSuccess(data: GetPostUseCase.Response): PostModel =
        PostModel(
            id = data.postWithUser.post.id,
            author = data.postWithUser.user.username,
            authorEmail = data.postWithUser.user.email,
            title = data.postWithUser.post.title,
            body = data.postWithUser.post.body
        )
}