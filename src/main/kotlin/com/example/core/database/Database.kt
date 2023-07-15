package com.example.core.database

import org.jetbrains.exposed.sql.Database

object Database {
    val instance by lazy {
        Database.connect(
            url = "jdbc:postgresql://localhost:6500/postgres?sslmode=disable",
            driver = "org.postgresql.Driver",
            user = "rdv_db",
            password = "rdv_db_password"
        )
    }
}