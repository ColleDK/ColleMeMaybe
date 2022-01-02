package dk.colle.collememaybe.ui.auth.login_user

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.colle.collememaybe.repository.BaseAuthRepository
import dk.colle.collememaybe.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class LoginUserViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: BaseAuthRepository
) : ViewModel() {

    private val TAG = "LoginViewModel"

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var email = mutableStateOf("")
        private set

    var password = mutableStateOf("")
        private set

    var showPasswordState = mutableStateOf(false)
        private set


    private fun loginUser(email: String, password: String) = viewModelScope.launch {
        try {
            repository.signInWithEmailPassword(email = email, password = password)
        } catch (e: Exception) {
            when(e){
                is FirebaseAuthInvalidCredentialsException -> {
                    val error = e.toString().split(":").toTypedArray()
                    Log.d(TAG, "Create user: ${error[1]}")
                    sendUiEvent(UiEvent.ShowSnackbar(
                        message = "Invalid credentials! Check for any errors"
                    ))
                }
                else -> {
                    val error = e.toString().split(":").toTypedArray()
                    Log.d(TAG, "Create user: ${error[1]}")
                }
            }
        }
    }

    private fun isNotEmptyInput(
        email: String,
        password: String,
    ): Boolean {
        return (email.isNotBlank() && password.isNotBlank())
    }

    fun onEvent(event: LoginUserEvent) {
        when (event) {
            is LoginUserEvent.OnEditEmail -> {
                email.value = event.email
            }
            is LoginUserEvent.OnEditPassword -> {
                password.value = event.password
            }
            is LoginUserEvent.OnLoginUser -> {
                if (isNotEmptyInput(email = email.value, password = password.value)) {
                    loginUser(email = email.value, password = password.value)
                } else {
                    sendUiEvent(
                        UiEvent.ShowSnackbar(
                            message = "Email and/or password cannot be empty"
                        )
                    )
                }
            }
            is LoginUserEvent.ToggleShowPassword -> {
                showPasswordState.value = !showPasswordState.value
            }
        }
    }

    private fun sendUiEvent(uiEvent: UiEvent) = viewModelScope.launch {
        _uiEvent.send(uiEvent)
    }
}