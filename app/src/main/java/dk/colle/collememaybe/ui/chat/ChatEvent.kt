package dk.colle.collememaybe.ui.chat

import dk.colle.collememaybe.dto.MessageDto

sealed class ChatEvent {
    data class OnEditMessage(val message: String) : ChatEvent()
    object OnSendMessage : ChatEvent()
    data class OnDeleteMessage(val message: MessageDto) : ChatEvent()
    data class OnLikeMessage(val messageId: String) : ChatEvent()
    data class OnReportMessage(val messageId: String) : ChatEvent()
    object OnUndo : ChatEvent()
}