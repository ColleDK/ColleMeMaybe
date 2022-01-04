package dk.colle.collememaybe.dto

import android.net.Uri

data class UserDto(
    val userId: String,
    val name: String,
    val age: Int,
    val email: String,
    val phoneNumber: String,
    val profilePic: Uri?,
)