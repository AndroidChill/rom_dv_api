package com.example.config.database

import com.example.config.DatabaseProvider
import com.example.config.DatabaseProviderContract
import org.koin.dsl.module

val databaseModule = module {
    single { Database.instance }
    single<DatabaseProviderContract> { DatabaseProvider() }
}