package com.example.feature.user

import com.example.feature.user.data.UserRepositoryImpl
import com.example.feature.user.domain.UserRepository
import com.example.feature.user.domain.UserService
import com.example.feature.user.presentation.UserController
import io.ktor.server.routing.*
import org.koin.dsl.module


val userModule = module {
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<UserService> { UserService(get()) }
    factory<UserController> { UserController(get()) }
}
