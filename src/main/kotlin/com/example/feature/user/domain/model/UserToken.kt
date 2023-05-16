package com.example.feature.user.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserToken (
    val token: String
)