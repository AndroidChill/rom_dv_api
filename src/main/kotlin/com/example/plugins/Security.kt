package com.example.plugins

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*

fun Application.configureSecurity() {

    authentication {
        jwt("jwt") {
            val jwtAudience = "user"
            realm = "rom_dv_jwt_realm"
            verifier(
                JWT
                    .require(Algorithm.HMAC256("rdv_api"))
                    .withAudience(jwtAudience)
                    .withIssuer("rom_dv_jwt_admin")
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(jwtAudience)) JWTPrincipal(credential.payload) else null
            }
        }
    }
}

fun createToken(nickname: String, password: String): String {
    val issuer = "rom_dv_jwt_admin"
    val audience = "user"
    val realm = "rom_dv_jwt_realm"

    return JWT.create()
        .withSubject("Authentication")
        .withIssuer(issuer)
        .withClaim("user_nickname", nickname)
        .withClaim("user_password", password)
//        .withExpiresAt(getExpiration)
        .sign(Algorithm.HMAC256("rdv_api"))
}