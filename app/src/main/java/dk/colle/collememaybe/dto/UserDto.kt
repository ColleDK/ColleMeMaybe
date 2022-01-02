package dk.colle.collememaybe.dto

import android.net.Uri

data class UserDto(
    val name: String,
    val age: Int,
    val description: String,
    val gender: Gender = Gender.Undefined,
    val email: String,
    val password: String,
    val phoneNumber: String,
    val pictures: MutableList<Uri> = mutableListOf()
)

enum class Gender {
    Male, Female, Undefined
}
