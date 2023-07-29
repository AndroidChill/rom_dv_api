package com.example.feature.user.model

import org.jetbrains.exposed.sql.Table


object UserTable : Table(name = "users") {
    val id = integer("id").autoIncrement()
    val email = varchar("email", length = 50)
    val phone = varchar("phone", length = 50)
    val firstName = varchar("first_name", length = 50)
    val secondName = varchar("second_name", length = 50)
    val thirdName = varchar("third_name", length = 50)
    val nickname = varchar("nickname", length = 50)
    val gender = integer("gender")
    val encryptedPassword = varchar("encrypted_password", length = 1000)

    override val primaryKey = PrimaryKey(id)
}