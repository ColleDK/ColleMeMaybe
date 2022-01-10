package dk.colle.collememaybe.model

import android.net.Uri
import dk.colle.collememaybe.dto.MessageDto
import java.util.*

data class MessageModel(
    val messageId: String,
    val senderId: String,
    val message: String?,
    val picture: Uri?,
    val timestamp: Date
) {
    companion object{
        fun MessageModel.toDto(): MessageDto {
            return MessageDto(
                messageId, senderId, message, picture, timestamp
            )
        }
    }
}
