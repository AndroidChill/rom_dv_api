package com.example.feature.auth

import org.koin.dsl.module

object AuthInjection {
    val koinBeans = module {
        single<AuthService>{ AuthService() }
        single<AuthRepository> { AuthRepository() }
    }
}