package com.example

import com.example.config.Config
import com.example.config.DatabaseProvider
import com.example.config.DatabaseProviderContract
import com.example.config.database.databaseModule
import com.example.feature.auth.AuthInjection
import com.example.feature.auth.authModule
import com.example.feature.friend.friendsModule
import com.example.feature.friend.presentation.FriendsController
import com.example.feature.questioning.presentation.QuestioningController
import com.example.feature.questioning.questioningModule
import com.example.feature.user.presentation.UserController
import com.example.feature.user.userModule
import com.example.plugins.*
import com.example.statuspages.authStatusPages
import com.example.statuspages.userStatusPages
import com.example.utils.JwtConfig
import com.typesafe.config.ConfigFactory
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.get
import org.koin.ktor.plugin.Koin
import org.slf4j.event.*

fun main() {
    val hoconConfig = HoconApplicationConfig(ConfigFactory.load())
    val config = extractConfig(getActualEnvironment(hoconConfig), HoconApplicationConfig(ConfigFactory.load()))
    embeddedServer(Netty, port = config.port, module = Application::start)
        .start(wait = true)
}

@Suppress("unused")
fun Application.start() {
    val hoconConfig = HoconApplicationConfig(ConfigFactory.load())
    val config = extractConfig(getActualEnvironment(hoconConfig), HoconApplicationConfig(ConfigFactory.load()))
    install(Koin) {
        modules(
            databaseModule,
            org.koin.dsl.module {
                single { config }
                single<DatabaseProviderContract> { DatabaseProvider() }
            },
            AuthInjection.koinBeans

        )
//        modules(userModule)
        modules(questioningModule)
        modules(friendsModule)

    }

    module()


}

val ApplicationCall.user get() = authentication.principal<UserPrincipal>()

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