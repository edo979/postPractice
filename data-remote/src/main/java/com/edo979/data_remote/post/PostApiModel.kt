package com.edo979.data_remote.post

import com.squareup.moshi.Json

data class PostApiModel(
    @Json(name = "id") val id: Long,
    @Json(name = "userId") val userId: Long,
    @Json(name = "title") val title: String,
    @Json(name = "body") val body: String
)
