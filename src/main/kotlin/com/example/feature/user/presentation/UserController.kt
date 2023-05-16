package com.example.feature.user.presentation

import com.example.feature.user.domain.UserService
import com.example.feature.user.domain.model.UserClaims
import com.example.feature.user.domain.model.UserRequest
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

class UserController(private val userService: UserService) {
    fun userRoute(route: Routing) {
        route {
            post("/sign_in") {
                val user = call.receive<UserClaims>()
                val response = userService.signIn(user.nickname, user.password)
                call.respond(response)
            }

            post("/sign_up") {
                val user = call.receive<UserRequest>()
                val response = userService.signUp(user)
                call.respond(response)
            }
        }
    }
//        route {

//        }



}