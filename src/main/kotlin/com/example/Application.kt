package com.example

import com.example.core.database.databaseModule
import com.example.feature.friend.friendsModule
import com.example.feature.friend.presentation.FriendsController
import com.example.feature.questioning.presentation.QuestioningController
import com.example.feature.questioning.questioningModule
import com.example.feature.user.presentation.UserController
import com.example.feature.user.userModule
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import com.example.utils.JwtConfig
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get
import org.koin.ktor.plugin.Koin

fun main() {
    embeddedServer(Netty, port = (System.getenv("PORT") ?: "8080").toInt(),  module = Application::module)
            .start(wait = true)
}

val ApplicationCall.user get() = authentication.principal<UserPrincipal>()
