package com.example.feature.questioning.domain

import com.example.feature.questioning.domain.model.QuestResponse
import com.example.feature.questioning.domain.model.UserQuestioningFillRequest

interface QuestioningRepository {

    suspend fun fillQuestioning(userId: Int, request: UserQuestioningFillRequest): List<Int>
    suspend fun getAllQuestioning(): List<QuestResponse>
}