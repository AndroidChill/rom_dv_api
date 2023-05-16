package com.example.feature.friend.domain

import com.example.feature.friend.domain.models.GetAllFriendsRequest
import com.example.feature.friend.domain.models.SearchFriendsRequest
import com.example.feature.friend.domain.models.SendRequestFriends
import com.example.feature.friend.domain.models.UpdateStateRequestFriends
import com.example.feature.user.domain.model.UserResponse

interface FriendsRepository {

    suspend fun getAllFriends(request: GetAllFriendsRequest) : List<Int>
    suspend fun getCountRequestFriends(userId: Int): Int

//    suspend fun searchFriends(request: SearchFriendsRequest) : List<Int>

    suspend fun sendRequestFriends(request: UpdateStateRequestFriends)
    suspend fun respondRequestFriends(request: UpdateStateRequestFriends)
}