package dk.colle.collememaybe.repository.user

import dk.colle.collememaybe.dto.UserDto
import dk.colle.collememaybe.repository.firebase.user.BaseFirebaseUser
import java.lang.Exception
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val firebaseUser: BaseFirebaseUser
) : BaseUserRepository {
    override suspend fun createUser(user: UserDto): UserDto {
        return firebaseUser.createUser(user)
    }

    override suspend fun updateUser(user: UserDto): UserDto {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser(userId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(userId: String): UserDto? {
        try {
            return firebaseUser.getUser(userId = userId)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getUsers(userIds: List<String>): List<UserDto>? {
        TODO("Not yet implemented")
    }

    override suspend fun getUsersFromServer(serverId: String): List<UserDto>? {
        TODO("Not yet implemented")
    }

    override suspend fun getAllUsers(): List<UserDto>? {
        TODO("Not yet implemented")
    }
}