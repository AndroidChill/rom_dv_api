package com.example.feature.questioning.data

import com.example.feature.questioning.domain.QuestioningRepository
import com.example.feature.questioning.domain.model.OptionResponse
import com.example.feature.questioning.domain.model.QuestResponse
import com.example.feature.questioning.domain.model.UserQuestioningFillRequest
import com.example.feature.questioning.domain.model.tables.OptionTable
import com.example.feature.questioning.domain.model.tables.ThemeTable
import com.example.feature.questioning.domain.model.tables.UserAnswerTable
import com.example.utils.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class QuestioningRepositoryImpl() : QuestioningRepository{

    override suspend fun fillQuestioning(userId: Int, request: UserQuestioningFillRequest): List<Int> = dbQuery {

        request.selectedData.map { themeFillRequest ->
            val optionSelected = themeFillRequest.optionSelected

            val optionId = optionSelected.optionId
            val customAnswer = optionSelected.customAnswer

            // Создаем запись в таблице UserAnswers
            UserAnswerTable.insert {
                it[UserAnswerTable.userForeignKey] = userId
                it[UserAnswerTable.optionForeignKey] = optionId ?: -1
                it[UserAnswerTable.customAnswer] = customAnswer ?: ""
            }[UserAnswerTable.id]
        }
    }

    override suspend fun getAllQuestioning(): List<QuestResponse> = dbQuery {
        ThemeTable.selectAll()
            .map {row ->
                val themeId = row[ThemeTable.id]
                val themeTitle = row[ThemeTable.name]

                val options = getOptionsForTheme(themeId)
                QuestResponse(
                    id = themeId,
                    title = themeTitle,
                    options = options
                )
            }
    }


    private fun getOptionsForTheme(themeId: Int): List<OptionResponse> {
        return OptionTable.select { OptionTable.themeForeignKey eq themeId }
            .map { row ->
                val optionId = row[OptionTable.id]
                val optionName = row[OptionTable.name]
                val imageUrl = row[OptionTable.imageUrl]
                OptionResponse(optionId, optionName, imageUrl)
            }
    }


}