package com.edo979.presentation_post.single

import com.edo979.domain.usecase.GetPostWithUserWithIsFavoriteUseCase
import com.edo979.presentation_common.state.CommonConverter
import javax.inject.Inject

class PostConverter @Inject constructor() :
    CommonConverter<GetPostWithUserWithIsFavoriteUseCase.Response, PostModel>() {

    override fun convertSuccess(data: GetPostWithUserWithIsFavoriteUseCase.Response): PostModel =
        PostModel(
            id = data.postWithData.post.id,
            author = data.postWithData.user.username,
            authorEmail = data.postWithData.user.email,
            title = data.postWithData.post.title,
            body = data.postWithData.post.body
        )
}