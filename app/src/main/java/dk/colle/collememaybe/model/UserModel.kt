package dk.colle.collememaybe.model

import android.net.Uri
import dk.colle.collememaybe.dto.UserDto
import java.util.*

data class UserModel(
    val userId: String,
    val name: String,
    val birthday: Date,
    val email: String,
    val phoneNumber: String,
    val profilePic: String?,
    val serverIds: List<String>,
    val chatIds: List<String>,
    val friendIds: List<String>
) {
    companion object{
        fun UserModel.toDto(): UserDto {
            return UserDto(
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
