package com.example.feature.questioning.domain.model.tables

import com.example.feature.user.model.UserTable
import org.jetbrains.exposed.sql.Table

object UserAnswerTable : Table(name = "user_answers") {
    val id = integer("id").autoIncrement()
    val customAnswer = varchar("custom_answer", length = 100)

    override val primaryKey = PrimaryKey(id)
    val userForeignKey = reference("user_id", UserTable.id)
    val optionForeignKey = reference("option_id", OptionTable.id)
}