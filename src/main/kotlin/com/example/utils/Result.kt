package com.example.utils

import kotlinx.serialization.Serializable

/**
* разделение на дату(сам ответ) и метадату(информация по статусу запроса)
* */
@Serializable
data class ResultResponse<T>(
    val data: T?,
    val metadata: MetaData?
)

@Serializable
data class MetaData(
    val code: Int?,
    val message: String?,
    val detail: String?
)