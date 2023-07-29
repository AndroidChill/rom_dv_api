package com.example.feature.questioning

import com.example.feature.questioning.data.QuestioningRepositoryImpl
import com.example.feature.questioning.domain.QuestioningRepository
import com.example.feature.questioning.domain.QuestioningService
import com.example.feature.questioning.presentation.QuestioningController
import org.koin.dsl.module

val questioningModule = module {
    single<QuestioningRepository> { QuestioningRepositoryImpl() }
    single<QuestioningService> { QuestioningService(get()) }
    factory<QuestioningController> { QuestioningController(get()) }
}