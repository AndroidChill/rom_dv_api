package com.example.statuspages

import io.ktor.http.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun StatusPagesConfig.userStatusPages() {
    exception<InvalidUserException> { call, cause ->
        call.respond(HttpStatusCode.BadRequest, cause.localizedMessage)
    }
    exception<UserNotExist> { call, _ ->
        call.respond(HttpStatusCode.NotFound)
    }

    exception<UserAlreadyExist> { call, _ ->
        call.respond(HttpStatusCode.BadRequest)
    }

    exception<UserIncorrectData> { call, cause ->
        call.respond(HttpStatusCode.UnprocessableEntity, cause.localizedMessage)
    }

}

data class InvalidUserException(override val message: String = "Invalid user") : Exception()

object UserNotExist : Exception()

object UserAlreadyExist : Exception()

class UserIncorrectData(override val message: String = "Check the correctness of the entered data") : Exception()
