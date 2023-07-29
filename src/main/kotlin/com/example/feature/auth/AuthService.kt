package com.example.feature.auth

import com.example.feature.auth.models.AuthToken
import com.example.feature.auth.models.SignUpRequest
import com.example.feature.user.model.UserFull
import com.example.feature.user.model.UserRequest
import com.example.feature.user.model.UserToken
import com.example.statuspages.*
import com.example.utils.*
import io.ktor.http.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.mindrot.jbcrypt.BCrypt

class AuthService : KoinComponent {

    private val repository by inject<AuthRepository>()

    suspend fun signIn(login: String, password: String): AuthToken {
        val user: UserFull? = if (login.contains("@")) {
            repository.getUserByEmail(login)
        } else {
            repository.getUserByPhone(login)
        }

        return user?.let {
            val hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt())
            if (BCrypt.checkpw(password, hashedPassword)) {
                val token = JwtConfig.generateToken(user.id, user.nickname, user.password)
                AuthToken(token)
            } else {
                throw UserIncorrectData()
            }
        } ?: throw UserIncorrectData()
    }

    suspend fun signUp(request: SignUpRequest): AuthToken {
        val existUser = repository.getUserByPhoneOrEmail(phone = request.phone, email = request.email)
        if (existUser != null) {
            val createdId = repository.create(request)
            val token = JwtConfig.generateToken(createdId, request.nickname, request.password)
            return AuthToken(token)
        } else {
            throw UserAlreadyExist
        }
    }

}