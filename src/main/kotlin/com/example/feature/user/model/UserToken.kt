package com.example.feature.user.model

import kotlinx.serialization.Serializable

@Serializable
data class UserToken (
    val token: String
)