package com.example.feature.auth

import com.example.feature.auth.models.SignUpRequest
import com.example.feature.user.domain.UserService
import com.example.feature.user.model.UserClaims
import com.example.feature.user.model.UserRequest
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.authModule() {
    val service by inject<AuthService>()

    post("/sign_in") {
        val user = call.receive<UserClaims>()
        val response = service.signIn(user.nickname, user.password)
        call.respond(response)
    }

    post("/sign_up") {
        val user = call.receive<SignUpRequest>()
        val response = service.signUp(user)
        call.respond(response)
    }
}