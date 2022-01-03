package dk.colle.collememaybe.dto

import android.net.Uri
import java.util.*

data class MessageDto(
    val messageId : String,
    val senderId: String,
    val message: String?,
    val picture: Uri?,
    val timestamp: Date
)
