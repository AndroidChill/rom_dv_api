package com.example.core.database

import org.koin.dsl.module

val databaseModule = module {
    single { Database.instance }
}