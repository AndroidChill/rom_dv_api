package com.example.feature.questioning.domain.model.tables

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object OptionTable : Table(name = "options") {
    val id = integer("id").autoIncrement()
//    val themeId = integer("theme_id")
    val name = varchar("name", length = 50)
    val imageUrl = varchar("image_url", length = 100)

    override val primaryKey = PrimaryKey(id)
    val themeForeignKey = reference("theme_id", ThemeTable.id)
}