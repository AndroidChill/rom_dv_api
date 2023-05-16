package com.example.feature.questioning

import com.example.feature.questioning.data.QuestioningRepositoryImpl
import com.example.feature.questioning.domain.QuestioningRepository
import com.example.feature.questioning.domain.QuestioningService
import com.example.feature.questioning.presentation.QuestioningController
import com.example.feature.user.data.UserRepositoryImpl
import com.example.feature.user.domain.UserRepository
import com.example.feature.user.domain.UserService
import com.example.feature.user.presentation.UserController
import org.koin.dsl.module

val questioningModule = module {
    single<QuestioningRepository> { QuestioningRepositoryImpl(get()) }
    single<QuestioningService> { QuestioningService(get()) }
    factory<QuestioningController> { QuestioningController(get()) }
}