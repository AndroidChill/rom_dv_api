package com.example

import com.example.config.Config
import com.example.config.database.databaseModule
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
import com.typesafe.config.ConfigFactory
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get
import org.koin.ktor.plugin.Koin

fun main() {
    val environment = System.getenv()["ENVIRONMENT"] ?: handleDefaultEnvironment()
    print("ENVIRONMENT" + environment)
    val hoconConfig = HoconApplicationConfig(ConfigFactory.load())
    val config = extractConfig(getActualEnvironment(hoconConfig), HoconApplicationConfig(ConfigFactory.load()))
    embeddedServer(Netty, port = config.port, module = Application::module)
        .start(wait = true)
}

@Suppress("unused")
fun Application.module() {
    install(Koin) {
        modules(databaseModule)
        modules(userModule)
        modules(questioningModule)
        modules(friendsModule)
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

    routing {
        val userController: UserController = get()
        val questioningController: QuestioningController = get()
        val friendsController: FriendsController = get()

        userController.userRoute(this)
        questioningController.questioningRoute(this)
        friendsController.friendsRoute(this)
    }

    configureHTTP()
    configureMonitoring()
    configureSerialization()
    configureDatabases()
    configureSockets()
    configureAdministration()
    configureRouting()
}

val ApplicationCall.user get() = authentication.principal<UserPrincipal>()

fun handleDefaultEnvironment(): String {
    println("Falling back to default environment 'dev'")
    return "dev"
}

fun getActualEnvironment(hoconConfig: HoconApplicationConfig): String {
    val hoconEnv = hoconConfig.config("ktor.deployment")
    return hoconEnv.property("environment").getString()
}

fun extractConfig(environment: String, hoconConfig: HoconApplicationConfig): Config {
    val hoconEnvironment = hoconConfig.config("ktor.deployment.$environment")
    val port = Integer.parseInt(hoconConfig.config("ktor.deployment").property("port").getString())
    return Config(
        host = hoconEnvironment.property("host").getString(),
        port = port,
        databaseHost = hoconEnvironment.property("databaseHost").getString(),
        databasePort = hoconEnvironment.property("databasePort").getString(),
        databaseUsername = hoconEnvironment.property("databaseUsername").getString(),
        databasePassword = hoconEnvironment.property("databasePassword").getString(),
        databaseName = hoconEnvironment.property("databaseName").getString()
    )
}