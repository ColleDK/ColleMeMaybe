package dk.colle.collememaybe.ui.chat

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.colle.collememaybe.dto.ChatDto
import dk.colle.collememaybe.dto.MessageDto
import dk.colle.collememaybe.repository.chat.BaseChatRepository
import dk.colle.collememaybe.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ChatScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: BaseChatRepository
) : ViewModel() {

    private val TAG = "ChatScreenVM"

    private lateinit var currentChatId: String

    private val _chat = MutableStateFlow<ChatDto?>(null)
    val chat = _chat.asStateFlow()

    private val _uiEvent = Channel<UiEvent>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        savedStateHandle.get<String>("chatId")?.let {
            currentChatId = it
            loadChat()
        }
    }

    private fun loadChat() = viewModelScope.launch {
        try {
            repository.getChat(chatId = currentChatId)
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
        }
    }

    private fun sendMessage(message: MessageDto) = viewModelScope.launch {
        try {
            repository.sendMessage(chatId = currentChatId, newMessage = message)
        } catch (e: Exception) {

        }
    }

    private fun deleteMessage(message: MessageDto) = viewModelScope.launch {
        try {
            repository.deleteMessage(chatId = currentChatId, message = message)
        } catch (e: Exception){

        }
    }


    fun onEvent(event: ChatEvent) {
        when (event) {
            is ChatEvent.OnSendMessage -> {
                sendMessage(message = event.message)
            }
            is ChatEvent.OnLikeMessage -> {

            }
            is ChatEvent.OnDeleteMessage -> {
                deleteMessage(message = event.message)
            }
            is ChatEvent.OnReportMessage -> {

            }
        }
    }

}