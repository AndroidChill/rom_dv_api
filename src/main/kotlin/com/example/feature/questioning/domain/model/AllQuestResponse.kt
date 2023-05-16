package com.example.feature.questioning.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class QuestResponse(

    val id: Int,

    @SerialName("title")
    val title: String,

    @SerialName("options")
    val options: List<OptionResponse>
)

@Serializable
data class OptionResponse(

    val id: Int,

    @SerialName("title")
    val title: String,

    @SerialName("image_url")
    val imageUrl: String?
)
