package com.example.feature.questioning.domain

import com.example.feature.questioning.domain.model.QuestResponse
import com.example.feature.questioning.domain.model.UserQuestioningFillRequest
import com.example.utils.DataIsIncorrectException
import com.example.utils.ResultResponse
import com.example.utils.success

class QuestioningService(
    private val questioningRepository: QuestioningRepository
) {

    suspend fun fillQuestioning(userId: Int, request: UserQuestioningFillRequest): ResultResponse<Int> {
        val response = questioningRepository.fillQuestioning(userId, request)
        return if (response.any { it < 1 }) {
            ResultResponse(
                data = null,
                metadata = DataIsIncorrectException
            )
        } else {
            ResultResponse(
                data = null,
                metadata = success
            )
        }
    }

    suspend fun getAllQuestioning(): ResultResponse<List<QuestResponse>> {
        val response = questioningRepository.getAllQuestioning()
        return ResultResponse(
            data = response,
            metadata = success
        )
    }

}