package com.ltu.m7019e.moviedb.v24.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Video(
    @SerialName(value = "name")
    var name: String,

    @SerialName(value = "key")
    var key: String,

    @SerialName(value = "site")
    var site: String,

//    @SerialName(value = "name")
//    var name: String,
)