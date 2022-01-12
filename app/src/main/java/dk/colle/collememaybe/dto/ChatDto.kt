package dk.colle.collememaybe.dto

import dk.colle.collememaybe.model.ChatModel
import dk.colle.collememaybe.model.MessageModel
import dk.colle.collememaybe.model.UserModel

data class ChatDto(
    val chatId: String = "",
    val users: List<String> = listOf(),
    val messages: List<String> = listOf()
) {
    companion object {
        fun ChatDto.toModel(userList: List<UserModel>, messageList: List<MessageModel>): ChatModel {
            return ChatModel(
                chatId = chatId,
                users = userList,
                messages = messageList
            )
        }
    }
}
