package com.ltu.m7019e.moviedb.v24.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideoResponse(
    @SerialName(value = "id")
    var id: Long = 0L,

    @SerialName(value = "results")
    var results: List<Video> = listOf(),
)