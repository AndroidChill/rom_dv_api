package com.example.feature.friend.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class SendRequestFriends(
    val status: String,
    val nickname: String
)

