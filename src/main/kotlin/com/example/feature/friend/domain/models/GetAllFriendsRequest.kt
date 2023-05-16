package com.example.feature.friend.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class GetAllFriendsRequest(
    val userId: Int?,
    val offset: Int,
    val limit: Int
)
