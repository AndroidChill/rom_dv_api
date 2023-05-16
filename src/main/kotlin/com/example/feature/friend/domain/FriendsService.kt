package com.example.feature.friend.domain

import com.example.feature.friend.domain.models.*
import com.example.feature.user.domain.UserRepository
import com.example.feature.user.domain.model.UserResponse
import com.example.utils.DataIsIncorrectException
import com.example.utils.ResultResponse
import com.example.utils.UserNotExistException
import com.example.utils.success

class FriendsService(
    private val friendsRepository: FriendsRepository,
    private val userRepository: UserRepository
) {

    suspend fun getAllFriends(request: GetAllFriendsRequest): ResultResponse<AllFriendsResponse> {

        if (request.userId == null) {
            return ResultResponse(
                data = null,
                metadata = UserNotExistException
            )
        }

        val userIds = friendsRepository.getAllFriends(request)
        val countRequest = friendsRepository.getCountRequestFriends(request.userId)
        return ResultResponse(
            data = AllFriendsResponse(
                friends = userRepository.getAllUsersByIds(userIds),
                countRequest = countRequest
            ),
            metadata = success
        )
    }

    suspend fun searchUser(request: SearchFriendsRequest): ResultResponse<SearchUserResponse> {
        val friendsResponse = getAllFriends(
            GetAllFriendsRequest(
                request.userId,
                offset = 0,
                limit = Int.MAX_VALUE
            )
        )

        if (friendsResponse.data == null) {
            return ResultResponse(
                data = null,
                metadata = friendsResponse.metadata
            )
        }

        val globalResponse = userRepository.searchUsersByFio(request.search)
        return ResultResponse(
            data = SearchUserResponse(
                friends = friendsResponse.data.friends.filter { it.nickname.contains(request.search) },
                global = globalResponse.filter { !friendsResponse.data.friends.contains(it) }
            ),
            metadata = success
        )

    }

    suspend fun activeRequestFriends(request: SendRequestFriends, userId: Int?): ResultResponse<Boolean> {

        if (userId == null) {
            return ResultResponse(
                data = null,
                metadata = UserNotExistException
            )
        }

        val friendId = userRepository.getUserByNickname(request.nickname)?.id
            ?: return ResultResponse(
                data = null,
                metadata = DataIsIncorrectException
            )

        if (request.status == "ACTIVE") {
            friendsRepository.sendRequestFriends(
                UpdateStateRequestFriends(
                    request.status, userId, friendId
                )
            )
        } else {
            friendsRepository.respondRequestFriends(
                UpdateStateRequestFriends(
                    request.status, userId, friendId
                )
            )
        }

        return ResultResponse(
            data = null,
            metadata = success
        )
    }

}