package dk.colle.collememaybe.dto

import android.net.Uri
import dk.colle.collememaybe.model.UserModel
import java.util.*

data class UserDto(
    val userId: String = "",
    val name: String = "",
    val birthday: Date = Date(),
    val email: String = "",
    val phoneNumber: String = "",
    val profilePic: String? = null,
    val serverIds: List<String> = listOf(),
    val chatIds: List<String> = listOf(),
    val friendIds: List<String> = listOf()
) {
    companion object {
        fun UserDto.toModel(): UserModel {
            return UserModel(
                userId,
                name,
                birthday,
                email,
                phoneNumber,
                profilePic,
                serverIds,
                chatIds,
                friendIds
            )
        }
    }
}