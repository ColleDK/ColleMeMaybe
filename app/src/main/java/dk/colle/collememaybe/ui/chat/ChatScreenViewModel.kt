package dk.colle.collememaybe.ui.chat

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.colle.collememaybe.dto.MessageDto
import dk.colle.collememaybe.model.ChatModel
import dk.colle.collememaybe.model.UserModel
import dk.colle.collememaybe.repository.auth.BaseAuthRepository
import dk.colle.collememaybe.repository.chat.BaseChatRepository
import dk.colle.collememaybe.repository.user.BaseUserRepository
import dk.colle.collememaybe.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ChatScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: BaseChatRepository,
    private val userRepository: BaseUserRepository,
    private val authRepository: BaseAuthRepository
) : ViewModel() {
    private val TAG = "ChatScreenVM"

    private var currentChatId: String

    // When undoing an action the lastAction will contain that action and corresponding message object
    private val lastAction: MutableState<LastAction?> = mutableStateOf(null)

    // The current chatobject
    private val _chat = MutableStateFlow<ChatModel?>(null)
    val chat = _chat.asStateFlow()

    // A map of usermodels for each senderid in the chat
    private val _senders = MutableStateFlow<HashMap<String, UserModel>>(hashMapOf())
    val senders = _senders.asStateFlow()

    // All ui events
    private val _uiEvent = Channel<UiEvent>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    // The usermodel of the current user
    private val _currentUser = MutableStateFlow<UserModel?>(null)
    val currentUser = _currentUser.asStateFlow()

    // The text state for creating a message
    var chatMessageState = mutableStateOf("")
        private set

    // Retrieve the current chatId from the navigation component ang retrieve the chat and user
    init {
        currentChatId = ""
        savedStateHandle.get<String>("chatId")?.let {
            currentChatId = it
            Log.d(TAG, "Current chat id is $currentChatId")
            loadChat()
        }
        getCurrentUser()
    }

    // Gets the current user from the firebase with the uid from the auth repository
    private fun getCurrentUser() = viewModelScope.launch {
        try {
            Log.d(TAG, "Getting current user")
            _currentUser.value =
                authRepository.getCurrentUser()?.uid?.let {
                    Log.d(TAG, "Current user id is $it")
                    Log.d(TAG, "Retrieving user with userid $it")
                    userRepository.getUser(it)
                }
            Log.d(TAG, "Current user value is now ${_currentUser.value.toString()}")
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
            Log.d(TAG, "getCurrentUser failed")
        }
    }

    // Retrieve a given chat from firebase
    private fun loadChat() = viewModelScope.launch {
        try {
            Log.d(TAG, "Loading chat with chatid $currentChatId!!")
            _chat.value = repository.getChat(chatId = currentChatId)
            Log.d(TAG, "Current chat value is ${_chat.value.toString()}")
        } catch (e: Exception) {
            Log.d(TAG, "Loading chat failed")
//            Log.d(TAG, e.message.toString())
        }
    }

    // Send a message in the chat in firebase
    private fun sendMessage(message: MessageDto) = viewModelScope.launch {
        try {
            val newMessage = repository.sendMessage(chatId = currentChatId, newMessage = message)
            newMessage?.let { lastAction.value = LastAction.SendMessage(message = it) }
            sendUiEvent(UiEvent.ShowSnackbar(message = "Message sent!", action = "Undo"))
        } catch (e: Exception) {

        }
    }

    // Delete a message from the chat in firebase
    private fun deleteMessage(message: MessageDto) = viewModelScope.launch {
        try {
            repository.deleteMessage(chatId = currentChatId, message = message)
            lastAction.value = LastAction.DeleteMessage(message = message)
            sendUiEvent(UiEvent.ShowSnackbar(message = "Message deleted!", action = "Undo"))
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
        }
    }


    // Retrieve the userobject of the sender of a given message and store in hashmap
    fun getSenderUser(userId: String) = viewModelScope.launch {
        try {
            // Only retrieve a user if not existing in current map
            if (!senders.value.containsKey(userId)) {
                val user = userRepository.getUser(userId)
                user?.let {
                    senders.value[userId] = it
                }
            }
        } catch (e: Exception) {
//            Log.d(TAG, e.message.toString())
            Log.d(TAG, "Getting sender with id $userId failed")
        }

    }

    // Retrieving an event from the view to the viewmodel
    fun onEvent(event: ChatEvent) {
        when (event) {

            is ChatEvent.OnSendMessage -> {
                _currentUser.value?.let {
                    MessageDto(
                        messageId = "",
                        senderId = it.userId,
                        message = chatMessageState.value,
                        picture = null,
                        timestamp = Date()
                    )
                }?.let {
                    sendMessage(
                        message = it
                    )
                }
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
                    is LastAction.LikeMessage -> {

                    }
                }
            }

            is ChatEvent.OnEditMessage -> {
                chatMessageState.value = event.message
            }
        }
    }

    // Send a ui event to the screen
    private fun sendUiEvent(uiEvent: UiEvent) = viewModelScope.launch {
        _uiEvent.send(uiEvent)
    }


    // The sealed class for last action performed
    sealed class LastAction {
        data class LikeMessage(val message: MessageDto) : LastAction()
        data class SendMessage(val message: MessageDto) : LastAction()
        data class DeleteMessage(val message: MessageDto) : LastAction()
        data class ReportMessage(val message: MessageDto) : LastAction()
    }

}