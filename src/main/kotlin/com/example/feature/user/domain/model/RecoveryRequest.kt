package com.example.feature.user.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecoveryRequest(
    @SerialName("login")
    val login: String
)
