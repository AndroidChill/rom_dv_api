package com.example.feature.questioning.presentation

import com.example.feature.questioning.domain.QuestioningService
import com.example.feature.questioning.domain.model.UserQuestioningFillRequest
import com.example.plugins.UserPrincipal
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

class QuestioningController(
    private val questioningService: QuestioningService
) {

    fun questioningRoute(route: Routing){
        route {
            authenticate {
                post("/fill_quest")  {
                    val userId = call.principal<UserPrincipal>()

                    val request = call.receive<UserQuestioningFillRequest>()
                    call.respond(questioningService.fillQuestioning(userId?.id ?: -1, request))
                }

                get("/get_quest") {
                    val userId = call.principal<UserPrincipal>()
                    call.respond(questioningService.getAllQuestioning())
                }
            }
            get("/get_quest") {
//                val userId = call.principal<UserPrincipal>()
                call.respond(questioningService.getAllQuestioning())
            }

        }

    }

}