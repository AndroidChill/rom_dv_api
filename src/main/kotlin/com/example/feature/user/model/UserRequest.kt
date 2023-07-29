package com.example.feature.user.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(

    @SerialName("email")
    val email: String,

    @SerialName("phone")
    val phone: String,

    @SerialName("fio")
    val fio: String,

    @SerialName("nickname")
    val nickname: String,

    @SerialName("gender")
    val gender: Int,

    @SerialName("password")
    var password: String
)