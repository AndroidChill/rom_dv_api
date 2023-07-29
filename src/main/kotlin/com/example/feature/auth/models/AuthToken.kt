package com.example.feature.auth.models

import kotlinx.serialization.Serializable

@Serializable
data class AuthToken(
    val token: String
)
