package com.example.feature.user.domain

import com.example.feature.user.data.UserRepository
import com.example.feature.user.model.RecoveryRequest
import com.example.feature.user.model.UserFull
import com.example.feature.user.model.UserRequest
import com.example.feature.user.model.UserToken
import com.example.utils.*
import io.ktor.http.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.mindrot.jbcrypt.BCrypt


class UserService : KoinComponent {

    private val userRepository by inject<UserRepository>()

    suspend fun signIn(login: String, password: String): ResultResponse<UserToken> {
        var user: UserFull? = null
        user = if (login.contains("@")) {
           userRepository.getUserByEmail(login)
        } else {
            userRepository.getUserByPhone(login)
        }

        return if (user == null) {
            ResultResponse(
                data = null,
                metadata = UserNotExistException
            )
        } else {
            val hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt())
            if (BCrypt.checkpw(password, hashedPassword)) {
                val token = JwtConfig.generateToken(user.id, user.nickname, user.password)
                ResultResponse(
                    data = UserToken(token),
                    metadata = MetaData(
                        HttpStatusCode.OK.value,
                        "token is successfully generated",
                        detail = null
                    )
                )
            } else {
                ResultResponse(
                    data = null,
                    metadata = DataIsIncorrectException
                )
            }
        }
    }

//    suspend fun signUp(request: UserRequest): ResultResponse<UserToken> {
//        val createdId = userRepository.create(request)
//        return if (createdId > 0) {
//            val token = JwtConfig.generateToken(createdId, request.nickname, request.password)
//            ResultResponse(
//                data = UserToken(token),
//                metadata = MetaData(
//                    HttpStatusCode.OK.value,
//                    "user is created generated",
//                    detail = null
//                )
//            )
//        } else {
//            ResultResponse(
//                data = null,
//                metadata = MetaData(
//                    HttpStatusCode.BadRequest.value,
//                    "user with such data already exists. Check email, phone and nickname",
//                    detail = null
//                )
//            )
//        }
//    }


}