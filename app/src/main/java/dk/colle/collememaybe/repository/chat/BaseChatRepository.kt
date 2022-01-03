package dk.colle.collememaybe.repository.chat

import dk.colle.collememaybe.dto.ChatDto
import dk.colle.collememaybe.dto.MessageDto

interface BaseChatRepository {
    suspend fun getChat(chatId: String) : ChatDto?
    suspend fun getChats() : List<ChatDto>?
    suspend fun createChat(newChat: ChatDto)
    suspend fun deleteChat(chatId: String)
    suspend fun sendMessage(chatId: String, newMessage: MessageDto)
    suspend fun deleteMessage(chatId: String, message: MessageDto)
}