package com.example.feature.user.data

import com.example.feature.auth.models.SignUpRequest
import com.example.feature.user.model.UserFull
import com.example.feature.user.model.UserRequest
import com.example.feature.user.model.UserResponse
import com.example.feature.user.model.UserTable
import com.example.utils.dbQuery
import org.jetbrains.exposed.sql.*
import org.koin.core.component.KoinComponent
import org.mindrot.jbcrypt.BCrypt

class UserRepository : KoinComponent {

    suspend fun getAllUser(): List<UserFull> = dbQuery {
        UserTable.selectAll().mapNotNull {
            it.toFullUser()
        }
    }

    suspend fun getAllUsersByIds(data: List<Int>): List<UserResponse> = dbQuery {
        UserTable.select {
            UserTable.id inList data
        }.mapNotNull {
            it.toUserResponse()
        }
    }

    suspend fun create(user: SignUpRequest): Int? = dbQuery {
        val hashedPassword = BCrypt.hashpw(user.password, BCrypt.gensalt())
        val fioData = user.fio.split(" ")
        UserTable.insert {
            it[email] = user.email
            it[phone] = user.phone
            it[firstName] = fioData.getOrNull(0) ?: ""
            it[secondName] = fioData.getOrNull(1) ?: ""
            it[thirdName] = fioData.getOrNull(2) ?: ""
            it[nickname] = user.nickname
            it[gender] = user.gender
            it[encryptedPassword] = hashedPassword
        }.getOrNull(UserTable.id)


    }

    suspend fun getUserByEmail(email: String): UserFull? = dbQuery {
        UserTable.select { UserTable.email eq email }
            .mapNotNull { row ->
                row.toFullUser()
            }
            .singleOrNull()
    }

    suspend fun getUserByPhone(phone: String): UserFull? = dbQuery {
        UserTable.select { UserTable.phone eq phone }
            .mapNotNull { row ->
                row.toFullUser()
            }
            .singleOrNull()
    }

    suspend fun getUserByNickname(nickname: String): UserFull? = dbQuery {
        UserTable.select { UserTable.nickname eq nickname }
            .mapNotNull { row ->
                row.toFullUser()
            }
            .singleOrNull()
    }


    suspend fun searchUsersByFio(search: String): List<UserResponse> = dbQuery {
        UserTable.select {
            UserTable.nickname.like("%$search%")
        }.mapNotNull {
            it.toUserResponse()
        }
    }

    private fun ResultRow.toUserResponse() =
        UserResponse(
            email = this[UserTable.email],
            phone = this[UserTable.phone],
            firstName = this[UserTable.firstName],
            secondName = this[UserTable.secondName],
            thirdName = this[UserTable.thirdName],
            nickname = this[UserTable.nickname],
            gender = this[UserTable.gender]
        )

    private fun ResultRow.toFullUser() =
        UserFull(
            id = this[UserTable.id],
            email = this[UserTable.email],
            phone = this[UserTable.phone],
            firstName = this[UserTable.firstName],
            secondName = this[UserTable.secondName],
            thirdName = this[UserTable.thirdName],
            nickname = this[UserTable.nickname],
            gender = this[UserTable.gender],
            password = BCrypt.hashpw(this[UserTable.encryptedPassword], BCrypt.gensalt())
        )
}
