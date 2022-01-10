package dk.colle.collememaybe.repository.firebase.message

import dk.colle.collememaybe.dto.MessageDto

interface BaseFirebaseMessage {
    suspend fun getMessage(messageId: String): MessageDto?
    suspend fun sendMessage(chatId: String, message: MessageDto): MessageDto?
    suspend fun deleteMessage(chatId: String, messageId: String)
    suspend fun updateMessage(messageId: String, newMessage: MessageDto): MessageDto?
}