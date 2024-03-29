package com.example.feature.user.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserClaims(
    @SerialName("phone")
    val nickname: String,

    @SerialName("password")
    val password: String
)
