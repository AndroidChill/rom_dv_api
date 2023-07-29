package com.example

import com.example.config.DatabaseProviderContract
import com.example.feature.auth.authModule
import com.example.plugins.*
import com.example.statuspages.authStatusPages
import com.example.statuspages.userStatusPages
import com.example.utils.JwtConfig
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.slf4j.event.Level

fun Application.module() {

    val databaseProvider by inject<DatabaseProviderContract>()
    databaseProvider.init()

    install(ContentNegotiation) {
        json()
    }

    install(CallLogging) {
        level = Level.DEBUG
    }

    install(StatusPages) {
        authStatusPages()
        userStatusPages()
        exception<UnknownError> { call, _ ->
            call.respondText(
                "Internal server error",
                ContentType.Text.Plain,
                status = HttpStatusCode.InternalServerError
            )
        }
        exception<IllegalArgumentException> { call, _ ->
            call.respond(HttpStatusCode.BadRequest)
        }
    }

    install(Authentication) {
        jwt {
            verifier(JwtConfig.verifier)
            realm = "com.example"
            validate {
                val id = it.payload.getClaim("id").asInt()
                val nickname = it.payload.getClaim("nickname").asString()
                val password = it.payload.getClaim("password").asString()
                if (nickname != null && password != null) {
                    UserPrincipal(id, nickname, password)
                } else {
                    null
                }
            }
        }
    }

    install(Routing) {
        authModule()
    }

//    routing {
//        val userController: UserController = get()
//        val questioningController: QuestioningController = get()
//        val friendsController: FriendsController = get()
//
//        userController.userRoute(this)
//        questioningController.questioningRoute(this)
//        friendsController.friendsRoute(this)
//    }

    configureHTTP()
    configureMonitoring()
    configureSerialization()
    configureDatabases()
    configureSockets()
    configureAdministration()
//    configureRouting()
}