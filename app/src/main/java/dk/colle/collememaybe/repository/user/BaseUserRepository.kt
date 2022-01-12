package dk.colle.collememaybe.repository.user

import dk.colle.collememaybe.dto.UserDto
import dk.colle.collememaybe.model.UserModel

interface BaseUserRepository {
    suspend fun createUser(user: UserDto): UserModel
    suspend fun updateUser(user: UserDto): UserModel
    suspend fun deleteUser(userId: String)
    suspend fun getUser(userId: String): UserModel?
    suspend fun getUsers(userIds: List<String>): List<UserModel>?
    suspend fun getUsersFromServer(serverId: String): List<UserModel>?
    suspend fun getAllUsers(): List<UserModel>?
}