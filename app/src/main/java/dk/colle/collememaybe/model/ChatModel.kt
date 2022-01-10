package dk.colle.collememaybe.model

import dk.colle.collememaybe.dto.ChatDto
import dk.colle.collememaybe.dto.MessageDto
import dk.colle.collememaybe.dto.UserDto

data class ChatModel(
    val chatId: String,
    val users: List<UserDto>,
    val messages: List<MessageDto>
) {
    companion object{
        fun ChatModel.toDto(): ChatDto {
            return ChatDto(
                chatId, users.map { it.userId }, messages.map { it.messageId }
            )
        }
    }
}
