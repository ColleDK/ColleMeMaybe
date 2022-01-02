package dk.colle.collememaybe.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.colle.collememaybe.repository.BaseAuthRepository
import dk.colle.collememaybe.ui.auth.create_user.CreateUserEvent
import dk.colle.collememaybe.util.Routes
import dk.colle.collememaybe.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CreateUserViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: BaseAuthRepository
) : ViewModel(

) {
    private val TAG = "MainViewModel"

    private var _user = repository.getCurrentUser()
    val user = _user

    private val _uiEvent = Channel<UiEvent>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    // Input states
    var name = mutableStateOf("")
        private set

    var age = mutableStateOf("")
        private set

    var email = mutableStateOf("")
        private set

    var password = mutableStateOf("")
        private set

    var phoneNumber = mutableStateOf("")
        private set

    var showPasswordState = mutableStateOf(true)
        private set

    var confirmButtonClickable = mutableStateOf(true)
        private set

    init {
        sendUiEvent(UiEvent.ShowSnackbar(message = "Test123"))
    }

    private fun signUpUser(email: String, password: String) = viewModelScope.launch {
        try {
            val firebaseUser =
                repository.signUpWithEmailPassword(email = email, password = password)
            firebaseUser?.let {
                _user = it
            }

        } catch (e: Exception) {
            val error = e.toString().split(":").toTypedArray()
            Log.d(TAG, "Create user: ${error[1]}")
        }
    }

    private fun isNotEmptyInput(
        name: String,
        age: String,
        email: String,
        password: String,
        phoneNumber: String
    ): Boolean {
        return (name.isNotBlank() && age.isNotBlank() && email.isNotBlank() && password.isNotBlank() && phoneNumber.isNotBlank())
    }


    fun onEvent(event: CreateUserEvent) {
        when (event) {
            is CreateUserEvent.OnEditName -> {
                name.value = event.name
            }
            is CreateUserEvent.OnEditAge -> {
                age.value = event.age
            }
            is CreateUserEvent.OnEditEmail -> {
                email.value = event.email
            }
            is CreateUserEvent.OnEditPassword -> {
                password.value = event.password
            }
            is CreateUserEvent.OnEditPhoneNumber -> {
                phoneNumber.value = event.phoneNumber
            }
            is CreateUserEvent.OnCreateUser -> {
                confirmButtonClickable.value = false
                // Check for inputs
                Log.d(TAG, "Event gotten with $event")
                if (isNotEmptyInput(
                        name = name.value,
                        age = age.value,
                        email = email.value,
                        password = password.value,
                        phoneNumber = phoneNumber.value
                    )
                ) {
                    Log.d(TAG, "Creating user")
                    signUpUser(
                        email = email.value,
                        password = password.value
                    )
                    sendUiEvent(UiEvent.Navigate(Routes.START_SCREEN))
                } else {
                    sendUiEvent(
                        UiEvent.ShowSnackbar(
                            message = "All inputs must be filled"
                        )
                    )
                    confirmButtonClickable.value = true
                }
            }
            is CreateUserEvent.ToggleShowPassword -> {
                showPasswordState.value = !showPasswordState.value
            }
        }
    }

    private fun sendUiEvent(uiEvent: UiEvent) {
        viewModelScope.launch {
            Log.d(TAG, "Sending new UI event $uiEvent")
            _uiEvent.send(uiEvent)
        }
    }


}