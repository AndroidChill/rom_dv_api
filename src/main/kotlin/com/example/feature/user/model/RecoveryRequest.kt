package com.example.feature.user.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecoveryRequest(
    @SerialName("login")
    val login: String
)
