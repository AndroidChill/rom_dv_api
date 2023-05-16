package com.example.core.database

import org.jetbrains.exposed.sql.Database

object Database {
    val instance by lazy {
        Database.connect(
            url = "jdbc:postgresql://database-1.czo2ysj77ual.eu-north-1.rds.amazonaws.com/postgres?sslmode=disable",
            driver = "org.postgresql.Driver",
            user = "rdv_db",
            password = "rdv_db_password"
        )
    }
}