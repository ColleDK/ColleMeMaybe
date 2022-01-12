package dk.colle.collememaybe.repository.chat

import dk.colle.collememaybe.dto.ChatDto
import dk.colle.collememaybe.dto.ChatDto.Companion.toModel
import dk.colle.collememaybe.dto.MessageDto
import dk.colle.collememaybe.dto.MessageDto.Companion.toModel
import dk.colle.collememaybe.dto.UserDto.Companion.toModel
import dk.colle.collememaybe.model.ChatModel
import dk.colle.collememaybe.model.MessageModel
import dk.colle.collememaybe.model.UserModel
import dk.colle.collememaybe.repository.firebase.auth.BaseAuthenticator
import dk.colle.collememaybe.repository.firebase.chat.BaseFirebaseChat
import dk.colle.collememaybe.repository.firebase.message.BaseFirebaseMessage
import dk.colle.collememaybe.repository.firebase.user.BaseFirebaseUser
import javax.inject.Inject

class ChatRepository @Inject constructor(
    private val authenticator: BaseAuthenticator,
    private val firebaseChat: BaseFirebaseChat,
    private val firebaseMessage: BaseFirebaseMessage,
    private val firebaseUser: BaseFirebaseUser
) : BaseChatRepository {

    override suspend fun getChat(chatId: String): ChatModel? {
        try {
            val messageList = mutableListOf<MessageModel>()
            val userList = mutableListOf<UserModel>()

            val chat = authenticator.getUser()?.uid?.let {
                firebaseChat.getChat(userId = it, chatId = chatId)
            }

            // Retrieve the messageobjects for the chat
            chat?.messages?.forEach { messageId ->
                firebaseMessage.getMessage(
                    messageId = messageId
                )?.toModel()?.let {
                    messageList.add(
                        it
                    )
                }
            }

            // Retrieve the userobjects for the chat
            chat?.users?.forEach { userId ->
                firebaseUser.getUser(
                    userId = userId
                )?.toModel()?.let {
                    userList.add(
                        it
                    )
                }
            }

            return chat?.toModel(messageList = messageList, userList = userList)
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

    override suspend fun sendMessage(chatId: String, newMessage: MessageDto): MessageDto? {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMessage(chatId: String, message: MessageDto) {
        TODO("Not yet implemented")
    }

}