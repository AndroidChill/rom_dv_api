package com.example.feature.questioning.domain.model.tables

import org.jetbrains.exposed.sql.Table

object ThemeTable : Table(name = "themes") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", length = 50)

    override val primaryKey = PrimaryKey(id)
}