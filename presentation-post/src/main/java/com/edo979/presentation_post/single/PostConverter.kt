package com.edo979.presentation_post.single

import com.edo979.domain.usecase.GetPostUseCase
import com.edo979.presentation_common.state.CommonConverter
import javax.inject.Inject

class PostConverter @Inject constructor() : CommonConverter<GetPostUseCase.Response, PostModel>() {

    override fun convertSuccess(data: GetPostUseCase.Response): PostModel {
        val (post, user, isFavorite) = data.post

        return PostModel(
            id = post.id,
            userId = user.id,
            userName = user.username,
            author = user.name,
            authorEmail = user.email,
            title = post.title,
            body = post.body,
            isFavorite = isFavorite
        )
    }
}