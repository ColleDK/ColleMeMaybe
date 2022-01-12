package dk.colle.collememaybe.repository.chat

import dk.colle.collememaybe.dto.ChatDto
import dk.colle.collememaybe.dto.MessageDto
import dk.colle.collememaybe.model.ChatModel

interface BaseChatRepository {
    suspend fun getChat(chatId: String): ChatModel?
    suspend fun getChats(): List<ChatDto>?
    suspend fun createChat(newChat: ChatDto)
    suspend fun deleteChat(chatId: String)
    suspend fun sendMessage(chatId: String, newMessage: MessageDto): MessageDto?
    suspend fun deleteMessage(chatId: String, message: MessageDto)
}