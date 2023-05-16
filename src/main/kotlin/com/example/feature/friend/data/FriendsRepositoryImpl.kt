package com.example.feature.friend.data

import com.example.feature.friend.domain.FriendsRepository
import com.example.feature.friend.domain.models.*
import com.example.feature.user.domain.model.UserResponse
import com.example.feature.user.domain.model.UserTable
import com.example.utils.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class FriendsRepositoryImpl(
    private val database: Database
) : FriendsRepository {

    private suspend fun countFriends(userId: Int) = dbQuery {
        FriendshipTable
            .select {
                FriendshipTable.userId eq userId
            }
            .count()
    }

    private suspend fun countRequestFriends(userId: Int) = dbQuery {
        FriendshipTable
            .slice(FriendshipTable.userId, FriendshipTable.friendId)
            .select {
                (FriendshipTable.userId eq userId) or (FriendshipTable.friendId eq userId)
            }
            .andWhere { FriendshipTable.statusId eq 2 }

    }

    private fun getStatusId(status: String): Int {
        return FriendsShipStatusTable
            .select { FriendsShipStatusTable.name eq status }
            .singleOrNull()?.get(FriendsShipStatusTable.id) ?: error("Friendship status not found")
    }

    private fun getFriendsId(userId: Int?, offset: Int, limit: Int, statusId: Int, search: String?) =
        FriendshipTable
            .select {
                (
                        (FriendshipTable.userId eq userId!!)
                                or (FriendshipTable.friendId eq userId)
                        ) and (FriendshipTable.statusId eq statusId)
            }
            .limit(limit, offset.toLong())
            .mapNotNull {
                if (it[FriendshipTable.friendId] == userId) {
                    it[FriendshipTable.userId]
                } else {
                    it[FriendshipTable.friendId]
                }
            }

    override suspend fun getCountRequestFriends(userId: Int): Int {
        return transaction {
            val statusId = getStatusId("ACTIVE")
            getFriendsId(userId, 0, Int.MAX_VALUE, statusId, null).count()
        }
    }

    override suspend fun getAllFriends(request: GetAllFriendsRequest): List<Int> {
        return transaction {
            val statusId = getStatusId("ACCEPTED")
            getFriendsId(request.userId, request.offset, request.limit, statusId, null)
        }
    }

//    override suspend fun searchFriends(request: SearchFriendsRequest): List<Int> {
//        return transaction {
//            val statusId = getStatusId("ACCEPTED")
//            val allSearchedUser
//        }
//    }


    override suspend fun sendRequestFriends(request: UpdateStateRequestFriends) {
        transaction {
            val statusId = getStatusId(request.state)
            FriendshipTable.insert {
                it[FriendshipTable.userId] = request.userId
                it[FriendshipTable.friendId] = request.friendId
                it[FriendshipTable.statusId] = statusId
            }
        }
    }

    override suspend fun respondRequestFriends(request: UpdateStateRequestFriends) {
        transaction {
            val statusId = getStatusId(request.state)
            FriendshipTable.update({
                (FriendshipTable.friendId eq request.userId) and (FriendshipTable.userId eq request.friendId)
            }) {
                it[FriendshipTable.statusId] = statusId
            }
        }
    }
}