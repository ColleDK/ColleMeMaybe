package dk.colle.collememaybe.repository.message

import dk.colle.collememaybe.dto.MessageDto

interface BaseMessageRepository {
    suspend fun getMessage(messageId: String): MessageDto?
    suspend fun sendMessage(chatId: String, message: MessageDto)
    suspend fun deleteMessage(chatId: String, messageId: String)
    suspend fun updateMessage(chatId: String, messageId: String, newMessage: MessageDto)
}