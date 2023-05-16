package com.example.feature.friend.domain.models

import org.jetbrains.exposed.sql.Table

object FriendsShipStatusTable: Table(name = "friendship_status") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", length = 20)

    override val primaryKey = PrimaryKey(id)
}