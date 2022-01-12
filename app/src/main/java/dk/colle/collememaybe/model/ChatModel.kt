package dk.colle.collememaybe.model

import dk.colle.collememaybe.dto.ChatDto

data class ChatModel(
    val chatId: String,
    val users: List<UserModel>,
    val messages: List<MessageModel>
) {
    companion object {
        fun ChatModel.toDto(): ChatDto {
            return ChatDto(
                chatId, users.map { it.userId }, messages.map { it.messageId }
            )
        }
    }
}
