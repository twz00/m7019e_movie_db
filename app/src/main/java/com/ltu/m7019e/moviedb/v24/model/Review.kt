package com.ltu.m7019e.moviedb.v24.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Review(
    @SerialName(value = "author")
    var author: String,

//    @SerialName(value = "author_details")
//    var author_details: AuthorDetails,

    @SerialName(value = "content")
    var content: String,

//    @SerialName(value = "created_at")
//    var created_at: String,
//
//    @SerialName(value = "id")
//    var id: String,
//
//    @SerialName(value = "updated_at")
//    var updated_at: String,
//
//    @SerialName(value = "url")
//    var url: String
)