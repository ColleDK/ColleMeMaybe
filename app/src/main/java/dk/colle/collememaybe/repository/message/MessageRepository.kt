package dk.colle.collememaybe.repository.message

import dk.colle.collememaybe.dto.MessageDto

class MessageRepository: BaseMessageRepository {
    override suspend fun getMessage(messageId: String): MessageDto? {
        TODO("Not yet implemented")
    }

    override suspend fun sendMessage(chatId: String, message: MessageDto) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMessage(chatId: String, messageId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updateMessage(chatId: String, messageId: String, newMessage: MessageDto) {
        TODO("Not yet implemented")
    }
}