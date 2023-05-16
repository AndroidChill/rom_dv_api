package com.example.plugins

import com.example.utils.JwtConfig
import io.ktor.server.auth.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import kotlinx.serialization.Serializable
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.SerialName
import org.jetbrains.exposed.sql.*
import org.mindrot.jbcrypt.BCrypt

@Serializable
data class UserProfile(
    val email: String,
    val phone: String,
    @SerialName("first_name")
    val firstName: String,
    @SerialName("second_name")
    val secondName: String,
    @SerialName("third_name")
    val thirdName: String,
    val nickname: String,
    val gender: Int,
)

@Serializable
data class UserLogin(
    val phone: String
)

data class UserPrincipal(
    val id: Int,
    val nickname: String,
    val password: String
): Principal

@Serializable
data class User(
    val email: String,
    val phone: String,

    @SerialName("first_name")
    val firstName: String,

    @SerialName("second_name")
    val secondName: String,

    @SerialName("third_name")
    val thirdName: String,
    val nickname: String,
    val gender: Int,
    var password: String
)
class UserService(private val database: Database) {
//    object Users : Table() {
//        val id = integer("id").autoIncrement()
//        val email = varchar("email", length = 50)
//        val phone = varchar("phone", length = 50)
//        val firstName = varchar("first_name", length = 50)
//        val secondName = varchar("second_name", length = 50)
//        val thirdName = varchar("third_name", length = 50)
//        val nickname = varchar("nickname", length = 50)
//        val gender = integer("gender")
//        val encryptedPassword = varchar("encrypted_password", length = 1000)
//
//        override val primaryKey = PrimaryKey(id)
//    }

//    init {
//        transaction(database) {
//            SchemaUtils.create(Users)
//        }
//    }
//
//    suspend fun <T> dbQuery(block: suspend () -> T): T =
//            newSuspendedTransaction(Dispatchers.IO) { block() }
//
//    suspend fun create(user: User): Int = dbQuery {
//        val hashedPassword = BCrypt.hashpw(user.password, BCrypt.gensalt())
//        Users.insert {
//            it[email] = user.email
//            it[phone] = user.phone
//            it[firstName] = user.firstName
//            it[secondName] = user.secondName
//            it[thirdName] = user.thirdName
//            it[nickname] = user.nickname
//            it[gender] = user.gender
//            it[encryptedPassword] = hashedPassword
//        }[Users.id]
//    }
//
//    fun decodePassword(password: String): String {
//        return BCrypt.hashpw(password, BCrypt.gensalt())
//    }
//
//    suspend fun getUserByEmail(email: String): User? = dbQuery {
//        Users.select { Users.email eq email }
//            .mapNotNull { row ->
//                val storedPassword = row[Users.encryptedPassword]
//                val hashedPassword = BCrypt.hashpw(storedPassword, BCrypt.gensalt())
//
//
//
//                if (BCrypt.checkpw(storedPassword, hashedPassword)) {
//                    User(
//                        email = row[Users.email],
//                        phone = row[Users.phone],
//                        firstName = row[Users.firstName],
//                        secondName = row[Users.secondName],
//                        thirdName = row[Users.thirdName],
//                        nickname = row[Users.nickname],
//                        gender = row[Users.gender],
//                        password = storedPassword
//                    )
//                } else {
//                    null
//                }
//            }
//            .singleOrNull()
//    }
//
//    suspend fun getUserByPhone(phone: String): String? = dbQuery {
//        Users.select { Users.phone eq phone }
//            .mapNotNull { row ->
//                val storedPassword = row[Users.encryptedPassword]
//                val hashedPassword = BCrypt.hashpw(storedPassword, BCrypt.gensalt())
//
//                if (BCrypt.checkpw(storedPassword, hashedPassword)) {
//                    JwtConfig.generateToken(row[Users.nickname], storedPassword)
//                } else {
//                    null
//                }
//            }
//            .singleOrNull()
//    }
//
//    suspend fun getUserByNickname(nickname: String): User? = dbQuery {
//        Users.select { Users.nickname eq nickname }
//            .mapNotNull { row ->
//                val storedPassword = row[Users.encryptedPassword]
//                val hashedPassword = BCrypt.hashpw(storedPassword, BCrypt.gensalt())
//
//                if (BCrypt.checkpw(storedPassword, hashedPassword)) {
//                    User(
//                        email = row[Users.email],
//                        phone = row[Users.phone],
//                        firstName = row[Users.firstName],
//                        secondName = row[Users.secondName],
//                        thirdName = row[Users.thirdName],
//                        nickname = row[Users.nickname],
//                        gender = row[Users.gender],
//                        password = storedPassword
//                    )
//                } else {
//                    null
//                }
//            }
//            .singleOrNull()
//    }

}