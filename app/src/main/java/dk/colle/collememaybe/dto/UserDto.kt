package dk.colle.collememaybe.dto

import android.net.Uri
import dk.colle.collememaybe.model.UserModel
import java.util.*

data class UserDto(
    val userId: String,
    val name: String,
    val birthday: Date,
    val email: String,
    val phoneNumber: String,
    val profilePic: Uri?,
    val serverIds: List<String>,
    val chatIds: List<String>,
    val friendIds: List<String>
) {
    companion object{
        fun UserDto.toModel(): UserModel {
            return UserModel(
                userId, name, birthday, email, phoneNumber, profilePic, serverIds, chatIds, friendIds
            )
        }
    }
}