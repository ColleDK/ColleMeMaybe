package dk.colle.collememaybe.dto

import android.net.Uri
import java.util.*

data class UserDto(
    val userId: String,
    val name: String,
    val birthday: Date,
    val email: String,
    val phoneNumber: String,
    val profilePic: Uri?,
)