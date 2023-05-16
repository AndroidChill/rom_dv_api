package com.example.feature.friend.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateStateRequestFriends(

    @SerialName("state")
    val state: String,

    @SerialName("user_id")
    val userId: Int,

    @SerialName("friend_id")
    val friendId: Int
)