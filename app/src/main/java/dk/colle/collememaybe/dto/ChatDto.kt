package dk.colle.collememaybe.dto

import dk.colle.collememaybe.model.ChatModel

data class ChatDto(
    val chatId: String,
    val users: List<String>,
    val messages: List<String>
) {
    // TODO fix
    companion object {
        fun ChatDto.toModel(): ChatModel {
            return ChatModel(
                chatId, listOf(), listOf()
            )
        }
    }
}
