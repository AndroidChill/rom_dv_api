package com.example

import com.example.Module.module
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import io.ktor.server.auth.*

fun main() {
    embeddedServer(Netty, port = (System.getenv("PORT") ?: "8080").toInt(),  module = Application::module)
            .start(wait = true)
}

val ApplicationCall.user get() = authentication.principal<UserPrincipal>()
