package com.example.feature.auth

import io.ktor.http.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun StatusPagesConfig.authStatusPages() {
    exception<AuthenticationException> { call, cause ->
        call.respondText(cause.message, ContentType.Text.Plain, status = HttpStatusCode.Unauthorized)
    }
    exception<AuthorizationException> { call, cause ->
        call.respondText(cause.message, ContentType.Text.Plain, status = HttpStatusCode.Forbidden)
    }
}

data class AuthenticationException(override val message: String = "Authentication failed") : Exception()
data class AuthorizationException(override val message: String = "You are not authorised to use this service") : Exception()
