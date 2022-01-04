package dk.colle.collememaybe.repository.chat

import dk.colle.collememaybe.dto.ChatDto
import dk.colle.collememaybe.dto.MessageDto
import dk.colle.collememaybe.repository.firebase.auth.BaseAuthenticator
import dk.colle.collememaybe.repository.firebase.chat.BaseFirebaseChat
import java.lang.Exception
import javax.inject.Inject

class ChatRepository @Inject constructor(
    private val authenticator: BaseAuthenticator,
    private val firebaseChat: BaseFirebaseChat
) : BaseChatRepository {

    override suspend fun getChat(chatId: String): ChatDto? {
        try {
            return authenticator.getUser()?.uid?.let {
                firebaseChat.getChat(userId = it, chatId = chatId)
            }
        } catch (e: Exception) {

        }
        return null
    }

    override suspend fun getChats(): List<ChatDto>? {
        try {
            return authenticator.getUser()?.let {
                firebaseChat.getChats(userId = it.uid)
            }
        } catch (e: Exception) {

        }
        return null
    }

    override suspend fun createChat(newChat: ChatDto) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteChat(chatId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun sendMessage(chatId: String, newMessage: MessageDto) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMessage(chatId: String, message: MessageDto) {
        TODO("Not yet implemented")
    }

}