package com.example.feature.questioning.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserQuestioningFillRequest(
    @SerialName("selected_data")
    val selectedData: List<ThemeFillRequest>
)

@Serializable
data class ThemeFillRequest(

    @SerialName("option_selected")
    val optionSelected: OptionFillRequest
)

@Serializable
data class OptionFillRequest(

    @SerialName("option_id")
    val optionId: Int?,

    @SerialName("custom_answer")
    val customAnswer: String?
)