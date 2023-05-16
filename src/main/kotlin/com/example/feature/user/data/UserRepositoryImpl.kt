package com.example.feature.user.data

import com.example.feature.user.domain.UserRepository
import com.example.feature.user.domain.model.UserFull
import com.example.feature.user.domain.model.UserRequest
import com.example.feature.user.domain.model.UserResponse
import com.example.feature.user.domain.model.UserTable
import com.example.plugins.User
import com.example.plugins.UserService
import com.example.utils.JwtConfig
import com.example.utils.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.mindrot.jbcrypt.BCrypt

class UserRepositoryImpl(
    private val database: Database
): UserRepository {

    init {
        transaction(database) {
            SchemaUtils.create(UserTable)
        }
    }

    override suspend fun getAllUser(): List<UserFull> = dbQuery {
        UserTable.selectAll().mapNotNull {
            it.toFullUser()
        }
    }

    override suspend fun getAllUsersByIds(data: List<Int>): List<UserResponse> = dbQuery {
        UserTable.select{
            UserTable.id inList data
        }.mapNotNull {
            it.toUserResponse()
        }
    }

    override suspend fun create(user: UserRequest): Int = dbQuery {
        val hashedPassword = BCrypt.hashpw(user.password, BCrypt.gensalt())
        UserTable.insert {
            it[email] = user.email
            it[phone] = user.phone
            it[firstName] = user.firstName
            it[secondName] = user.secondName
            it[thirdName] = user.thirdName
            it[nickname] = user.nickname
            it[gender] = user.gender
            it[encryptedPassword] = hashedPassword
        }[UserTable.id]
    }

    fun decodePassword(password: String): String {
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }

    override suspend fun getUserByEmail(email: String): UserFull? = dbQuery {
        UserTable.select { UserTable.email eq email }
            .mapNotNull { row ->
                row.toFullUser()
            }
            .singleOrNull()
    }

    override suspend fun getUserByPhone(phone: String): UserFull? = dbQuery {
        UserTable.select { UserTable.phone eq phone }
            .mapNotNull { row ->
                row.toFullUser()
            }
            .singleOrNull()
    }

    override suspend fun getUserByNickname(nickname: String): UserFull? = dbQuery {
        UserTable.select { UserTable.nickname eq nickname }
            .mapNotNull { row ->
                row.toFullUser()
            }
            .singleOrNull()
    }

    override suspend fun searchUsersByFio(search: String): List<UserResponse> = dbQuery {
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
