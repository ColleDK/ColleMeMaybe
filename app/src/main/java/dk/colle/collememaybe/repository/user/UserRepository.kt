package dk.colle.collememaybe.repository.user

import dk.colle.collememaybe.dto.UserDto
import dk.colle.collememaybe.dto.UserDto.Companion.toModel
import dk.colle.collememaybe.model.UserModel
import dk.colle.collememaybe.repository.firebase.user.BaseFirebaseUser
import java.lang.Exception
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val firebaseUser: BaseFirebaseUser
) : BaseUserRepository {
    override suspend fun createUser(user: UserDto): UserModel {
        return firebaseUser.createUser(user).toModel()
    }

    override suspend fun updateUser(user: UserDto): UserModel {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser(userId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(userId: String): UserModel? {
        try {
            return firebaseUser.getUser(userId = userId)?.toModel()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getUsers(userIds: List<String>): List<UserModel>? {
        TODO("Not yet implemented")
    }

    override suspend fun getUsersFromServer(serverId: String): List<UserModel>? {
        TODO("Not yet implemented")
    }

    override suspend fun getAllUsers(): List<UserModel>? {
        TODO("Not yet implemented")
    }
}