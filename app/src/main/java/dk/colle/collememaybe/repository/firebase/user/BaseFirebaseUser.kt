package dk.colle.collememaybe.repository.firebase.user

import dk.colle.collememaybe.dto.UserDto

interface BaseFirebaseUser {
    suspend fun createUser(user: UserDto): UserDto
    suspend fun updateUser(user: UserDto): UserDto
    suspend fun deleteUser(userId: String)
    suspend fun getUser(userId: String) : UserDto?
    suspend fun getUsers(userIds: List<String>): List<UserDto>?
    suspend fun getUsersFromServer(serverId: String): List<UserDto>?
    suspend fun getAllUsers(): List<UserDto>?
}