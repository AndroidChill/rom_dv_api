package com.example.feature.friend.domain.models

import com.example.feature.user.domain.model.UserResponse
import kotlinx.serialization.Serializable

@Serializable
data class SearchUserResponse(
    val friends: List<UserResponse>,
    val global: List<UserResponse>
)
