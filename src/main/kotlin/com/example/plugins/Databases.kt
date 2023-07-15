package com.example.plugins

import com.example.user
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.coroutines.*
import java.sql.*
import org.jetbrains.exposed.sql.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Application.configureDatabases() {

    val database = Database.connect(
        url = "jdbc:postgresql://localhost:6500/postgres?sslmode=disable",
        driver = "org.postgresql.Driver",
        user = "rdv_db",
        password = "rdv_db_password"
    )
    val dbConnection: Connection = connectToPostgres(embedded = true)
//    val cityService = CityService(dbConnection)
    val userService = UserService(database)
    routing {

//        post("/registration") {
//            val user = call.receive<User>()
//            val id = userService.create(user)
//            call.respond(HttpStatusCode.Created, id)
//        }
//
//        post("/login") {
//            val phone = call.receive<UserLogin>()
//            val token = userService.getUserByPhone(phone.phone)
//            call.respond(HttpStatusCode.OK, token ?: "invalid token")
//        }
//
//        authenticate {
//            get("/profile") {
//                if (call.user != null) {
//                    val user = userService.getUserByNickname(call.user!!.nickname)
//                    if (user != null) {
//                        call.respond(UserProfile(
//                            email = user.email,
//                            phone = user.phone,
//                            firstName = user.firstName,
//                            secondName = user.secondName,
//                            thirdName = user.thirdName,
//                            nickname = user.nickname,
//                            gender = user.gender
//                        ))
//                    }
//                }
//            }
//        }

    }
}

/**
 * Makes a connection to a Postgres database.
 *
 * In order to connect to your running Postgres process,
 * please specify the following parameters in your configuration file:
 * - postgres.url -- Url of your running database process.
 * - postgres.user -- Username for database connection
 * - postgres.password -- Password for database connection
 *
 * If you don't have a database process running yet, you may need to [download]((https://www.postgresql.org/download/))
 * and install Postgres and follow the instructions [here](https://postgresapp.com/).
 * Then, you would be able to edit your url,  which is usually "jdbc:postgresql://host:port/database", as well as
 * user and password values.
 *
 *
 * @param embedded -- if [true] defaults to an embedded database for tests that runs locally in the same process.
 * In this case you don't have to provide any parameters in configuration file, and you don't have to run a process.
 *
 * @return [Connection] that represent connection to the database. Please, don't forget to close this connection when
 * your application shuts down by calling [Connection.close]
 * */
fun Application.connectToPostgres(embedded: Boolean): Connection {
    Class.forName("org.postgresql.Driver")
    if (embedded) {
        return DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "root", "")
    } else {
        val url = environment.config.property("postgres.url").getString()
        val user = environment.config.property("postgres.user").getString()
        val password = environment.config.property("postgres.password").getString()

        return DriverManager.getConnection(url, user, password)
    }
}
