package dk.colle.collememaybe.dto

import android.net.Uri
import dk.colle.collememaybe.model.MessageModel
import java.util.*

data class MessageDto(
    val messageId: String,
    val senderId: String,
    val message: String?,
    val picture: Uri?,
    val timestamp: Date
) {
    companion object{
        fun MessageDto.toModel(): MessageModel {
            return MessageModel(
                messageId, senderId, message, picture, timestamp
            )
        }
    }
}
