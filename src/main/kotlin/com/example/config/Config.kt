package com.example.config

data class Config(
    val host: String,
    val port: Int,
    val databaseHost: String,
    val databasePort: String,
    val databaseUsername: String,
    val databasePassword: String,
    val databaseName: String
)