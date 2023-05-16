package com.example.feature.friend.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchFriendsRequest(

    @SerialName("user_id")
    val userId: Int?,

    @SerialName("search")
    val search: String
)

@Serializable
data class SearchUserRequest(
    val search: String
)
