package com.example.feature.friend.domain.models

import com.example.feature.user.model.UserResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AllFriendsResponse(

    @SerialName("friends")
    val friends: List<UserResponse>,

    @SerialName("count_request")
    val countRequest: Int
)