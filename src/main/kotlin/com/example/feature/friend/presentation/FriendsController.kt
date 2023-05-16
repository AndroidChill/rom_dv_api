package com.example.feature.friend.presentation

import com.example.feature.friend.domain.FriendsService
import com.example.feature.friend.domain.models.*
import com.example.plugins.UserPrincipal
import com.example.utils.models.PaginateRequest
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

class FriendsController(
    private val friendsService: FriendsService
) {

    fun friendsRoute(route: Routing) {
        route {
            authenticate {
                post("/friends") {
                    val userId = call.principal<UserPrincipal>()?.id
                    val request = call.receive<PaginateRequest>()

                    call.respond(
                        friendsService.getAllFriends(
                            GetAllFriendsRequest(
                                userId = userId,
                                offset = request.offset,
                                limit = request.limit
                            )
                        )
                    )
                }

                post ( "/friends/search")  {
                    val userId = call.principal<UserPrincipal>()?.id
                    val request = call.receive<SearchUserRequest>()

                    call.respond(
                        friendsService.searchUser(
                            SearchFriendsRequest(
                                userId,
                                request.search
                            )
                        )
                    )
                }

                post ("/friends/action") {
                    val userId = call.principal<UserPrincipal>()?.id
                    val request = call.receive<SendRequestFriends>()

                    call.respond(
                        friendsService.activeRequestFriends(request, userId)
                    )
                }
            }
        }
    }

}