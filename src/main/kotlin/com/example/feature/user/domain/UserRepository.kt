package com.example.feature.user.domain

import com.example.feature.user.domain.model.UserFull
import com.example.feature.user.domain.model.UserRequest
import com.example.feature.user.domain.model.UserResponse

interface UserRepository {

    suspend fun create(user: UserRequest): Int
    suspend fun getUserByEmail(email: String): UserFull?
    suspend fun getUserByPhone(phone: String): UserFull?
    suspend fun getUserByNickname(nickname: String): UserFull?

    suspend fun getAllUser(): List<UserFull>

    suspend fun getAllUsersByIds(data: List<Int>): List<UserResponse>
    suspend fun searchUsersByFio(search: String): List<UserResponse>
}