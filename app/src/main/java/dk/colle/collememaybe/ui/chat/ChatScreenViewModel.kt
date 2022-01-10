package dk.colle.collememaybe.ui.chat

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.colle.collememaybe.dto.ChatDto.Companion.toModel
import dk.colle.collememaybe.dto.MessageDto
import dk.colle.collememaybe.dto.UserDto
import dk.colle.collememaybe.model.ChatModel
import dk.colle.collememaybe.repository.chat.BaseChatRepository
import dk.colle.collememaybe.repository.user.BaseUserRepository
import dk.colle.collememaybe.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: BaseChatRepository,
    private val userRepository: BaseUserRepository,
) : ViewModel() {

    private val TAG = "ChatScreenVM"

    private var currentChatId: String
    private val lastAction: MutableState<LastAction?> = mutableStateOf(null)
    private val deletedMessage = mutableStateOf<MessageDto?>(null)

    private val _chat = MutableStateFlow<ChatModel?>(null)
    val chat = _chat.asStateFlow()

    private val _senders = MutableStateFlow<HashMap<String, UserDto>>(hashMapOf())
    val senders = _senders.asStateFlow()

    private val _uiEvent = Channel<UiEvent>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        currentChatId = ""
        savedStateHandle.get<String>("chatId")?.let {
            currentChatId = it
            Log.d(TAG, "Current chat id is $currentChatId")
            loadChat()
        }
    }

    private fun loadChat() = viewModelScope.launch {
        try {
            _chat.value = repository.getChat(chatId = currentChatId)?.toModel()
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
        }
    }

    private fun sendMessage(message: MessageDto) = viewModelScope.launch {
        try {
            repository.sendMessage(chatId = currentChatId, newMessage = message)
            lastAction.value = LastAction.SendMessage
            sendUiEvent(UiEvent.ShowSnackbar(message = "Message sent!", action = "Undo"))
        } catch (e: Exception) {

        }
    }

    private fun deleteMessage(message: MessageDto) = viewModelScope.launch {
        try {
            repository.deleteMessage(chatId = currentChatId, message = message)
            lastAction.value = LastAction.DeleteMessage
            deletedMessage.value = message
            sendUiEvent(UiEvent.ShowSnackbar(message = "Message deleted!", action = "Undo"))
        } catch (e: Exception) {

        }
    }


    fun getSenderUser(userId: String) = viewModelScope.launch {
        try {
            if (!senders.value.containsKey(userId)) {
                val user = userRepository.getUser(userId)
                user?.let {
                    senders.value[userId] = it
                }
            }
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
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
            is ChatEvent.OnUndo -> {
                when (lastAction.value) {
                    is LastAction.SendMessage -> {
                        //TODO
                    }
                    is LastAction.ReportMessage -> {
                        //TODO
                    }
                    is LastAction.DeleteMessage -> {
                        //TODO
                    }
                }
            }
        }
    }

    private fun sendUiEvent(uiEvent: UiEvent) = viewModelScope.launch {
        _uiEvent.send(uiEvent)
    }


    sealed class LastAction {
        object SendMessage : LastAction()
        object DeleteMessage : LastAction()
        object ReportMessage : LastAction()
    }

}