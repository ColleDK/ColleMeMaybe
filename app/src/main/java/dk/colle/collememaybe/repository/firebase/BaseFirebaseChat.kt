package dk.colle.collememaybe.repository.firebase

import dk.colle.collememaybe.dto.ChatDto

interface BaseFirebaseChat {
    suspend fun getChat(userId: String, chatId: String) : ChatDto?
    suspend fun getChats(userId: String) : List<ChatDto>?
    suspend fun createChat(newChat: ChatDto) : String
    suspend fun deleteChat(chatId: String)
    suspend fun updateChat(chatId: String, chatDto: ChatDto)
}