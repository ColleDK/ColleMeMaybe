package dk.colle.collememaybe.dto

data class ChatDto(
    val chatId: String,
    val users: List<String>,
    val messages: List<MessageDto>
)
