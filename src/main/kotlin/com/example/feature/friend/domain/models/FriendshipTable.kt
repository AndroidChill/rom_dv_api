package com.example.feature.friend.domain.models

import com.example.feature.user.domain.model.UserTable
import org.jetbrains.exposed.sql.Table

object FriendshipTable: Table(name = "friendship") {

    val statusId = reference("status_id", FriendsShipStatusTable.id)
    val userId = reference("user_id", UserTable.id)
    val friendId = reference("friend_id", UserTable.id)
}