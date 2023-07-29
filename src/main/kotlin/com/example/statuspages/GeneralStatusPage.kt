package com.example.statuspages

import io.ktor.http.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun StatusPagesConfig.generalStatusPages() {
    exception<MissingArgumentException> { call, cause ->
        call.respond(HttpStatusCode.BadRequest, cause.message)
    }
    exception<InternetServerException> { call, cause ->
        call.respond(HttpStatusCode.InternalServerError, cause.message)
    }
}

data class MissingArgumentException(override val message: String = "Missing argument") : Exception()

data class InternetServerException(override val message: String = "Internet server error") : Exception()