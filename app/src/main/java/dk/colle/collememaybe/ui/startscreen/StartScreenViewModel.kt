package dk.colle.collememaybe.ui.startscreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.colle.collememaybe.model.ServerModel
import dk.colle.collememaybe.repository.auth.BaseAuthRepository
import dk.colle.collememaybe.util.Routes
import dk.colle.collememaybe.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    repository: BaseAuthRepository
) : ViewModel() {
    private val _servers = MutableStateFlow<List<ServerModel>>(listOf())
    val servers = _servers.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent


    private fun getFriendsList() = viewModelScope.launch {

    }

    fun onEvent(event: StartScreenEvent) {
        when (event) {
            is StartScreenEvent.OnFriendslistClick -> {

            }
            is StartScreenEvent.OnDMClick -> {
                sendUiEvent(UiEvent.Navigate(route = "${Routes.CHAT_SCREEN}/${event.chatId}"))
            }
            is StartScreenEvent.OnServerClick -> {

            }
        }

    }

    private fun sendUiEvent(event: UiEvent) = viewModelScope.launch {
        _uiEvent.send(event)
    }
}