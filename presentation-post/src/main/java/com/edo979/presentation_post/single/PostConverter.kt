package com.edo979.presentation_post.single

import com.edo979.domain.usecase.GetSinglePostWithUserUseCase
import com.edo979.presentation_common.state.CommonConverter
import javax.inject.Inject

class PostConverter @Inject constructor() :
    CommonConverter<GetSinglePostWithUserUseCase.Response, PostModel>() {

    override fun convertSuccess(data: GetSinglePostWithUserUseCase.Response): PostModel =
        PostModel(
            id = data.data.post.id,
            author = data.data.user.username,
            authorEmail = data.data.user.email,
            title = data.data.post.title,
            body = data.data.post.body
        )
}