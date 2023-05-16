package com.example.feature.friend

import com.example.feature.friend.data.FriendsRepositoryImpl
import com.example.feature.friend.domain.FriendsRepository
import com.example.feature.friend.domain.FriendsService
import com.example.feature.friend.presentation.FriendsController
import com.example.feature.user.data.UserRepositoryImpl
import com.example.feature.user.domain.UserRepository
import org.koin.dsl.module

val friendsModule = module {
    single<FriendsRepository> { FriendsRepositoryImpl(get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<FriendsService> { FriendsService(get(), get()) }
    single<FriendsController> { FriendsController(get()) }
}