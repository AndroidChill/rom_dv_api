package com.example.utils.models

import kotlinx.serialization.Serializable

@Serializable
data class PaginateRequest(
    val offset: Int,
    val limit: Int
)
