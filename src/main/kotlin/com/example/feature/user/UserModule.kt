package com.example.feature.user

import com.example.feature.auth.AuthService
import com.example.feature.auth.models.SignUpRequest
import com.example.feature.user.data.UserRepository
import com.example.feature.user.model.UserClaims
import com.example.feature.user.model.UserRequest
import com.example.feature.user.presentation.UserController
import com.example.plugins.UserService
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.dsl.module
import org.koin.ktor.ext.inject


//val userModule = module {
//    single<UserRepository> { UserRepository() }
//    single<UserService> { UserService() }
//    factory<UserController> { UserController(get()) }
//}

fun Route.userModule() {
    val service by inject<AuthService>()

    post("/sign_in") {
        val user = call.receive<UserClaims>()
        val response = service.signIn(user.nickname, user.password)
        call.respond(response)
    }

    post("/sign_up") {
//        val user = call.receive<SignUpRequest>()
//        val response = service.signUp(user)
//        call.respond(response)
    }
    get("/qw") {
        call.respond("sdf")
    }
}