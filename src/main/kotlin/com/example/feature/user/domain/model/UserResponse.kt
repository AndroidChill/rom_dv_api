package com.example.feature.user.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(

    @SerialName("email")
    val email: String,

    @SerialName("phone")
    val phone: String,

    @SerialName("first_name")
    val firstName: String,

    @SerialName("second_name")
    val secondName: String,

    @SerialName("third_name")
    val thirdName: String,

    @SerialName("nickname")
    val nickname: String,

    @SerialName("gender")
    val gender: Int,
)
