package dk.colle.collememaybe.ui.auth.create_user

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.colle.collememaybe.dto.UserDto
import dk.colle.collememaybe.repository.auth.BaseAuthRepository
import dk.colle.collememaybe.repository.firebase.user.BaseFirebaseUser
import dk.colle.collememaybe.util.CurrentDateFormatter
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
    private val repository: BaseAuthRepository,
    private val firebaseUser: BaseFirebaseUser
) : ViewModel(

) {
    private val TAG = "MainViewModel"

    private val _uiEvent = Channel<UiEvent>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    // Input states
    var name = mutableStateOf("")
        private set

    var birthday = mutableStateOf("")
        private set

    var email = mutableStateOf("")
        private set

    var password = mutableStateOf("")
        private set

    var phoneNumber = mutableStateOf("")
        private set

    var showPasswordState = mutableStateOf(false)
        private set

    var confirmButtonClickable = mutableStateOf(true)
        private set

    private fun signUpUser(
        name: String,
        birthday: String,
        email: String,
        password: String,
        phoneNumber: String
    ) = viewModelScope.launch {
        try {
            val result = repository.signUpWithEmailPassword(email = email, password = password)
            if (result != null) {
                CurrentDateFormatter.DATEFORMATTER.parse(birthday)?.let {
                    firebaseUser.createUser(
                        UserDto(
                            userId = "",
                            name = name,
                            birthday = it,
                            email = email,
                            phoneNumber = phoneNumber,
                            profilePic = null
                        )
                    )
                    sendUiEvent(UiEvent.Navigate(Routes.START_SCREEN))
                }
            } else {
                sendUiEvent(UiEvent.ShowSnackbar(message = "An unexpected error has occured please try again"))
            }
        } catch (e: Exception) {
            when (e) {
                is FirebaseAuthUserCollisionException -> {
                    val error = e.toString().split(":").toTypedArray()
                    Log.d(TAG, "Create user: ${error[1]}")
                    sendUiEvent(
                        UiEvent.ShowSnackbar(
                            message = "E-mail is already used! Try to login instead",
                            action = "Go to login screen"
                        )
                    )
                    confirmButtonClickable.value = true
                }
                is FirebaseAuthWeakPasswordException -> {
                    val error = e.toString().split(":").toTypedArray()
                    Log.d(TAG, "Create user: ${error[1]}")
                    sendUiEvent(UiEvent.ShowSnackbar(message = "Password is too weak"))
                    confirmButtonClickable.value = true
                }
                else -> {
                    val error = e.toString().split(":").toTypedArray()
                    Log.d(TAG, "Create user: ${error[1]}")
                    confirmButtonClickable.value = true
                }
            }
        }
    }

    private fun isNotEmptyInput(
        name: String,
        birthday: String,
        email: String,
        password: String,
        phoneNumber: String
    ): Boolean {
        return (name.isNotBlank() && birthday.isNotBlank() && email.isNotBlank() && password.isNotBlank() && phoneNumber.isNotBlank())
    }


    fun onEvent(event: CreateUserEvent) {
        when (event) {
            is CreateUserEvent.OnEditName -> {
                name.value = event.name
            }
            is CreateUserEvent.OnEditBirthday -> {
                birthday.value = event.birthday
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
                        birthday = birthday.value,
                        email = email.value,
                        password = password.value,
                        phoneNumber = phoneNumber.value
                    )
                ) {
                    Log.d(TAG, "Creating user")
                    signUpUser(
                        name = name.value,
                        birthday = birthday.value,
                        email = email.value,
                        password = password.value,
                        phoneNumber = phoneNumber.value
                    )
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
            is CreateUserEvent.OnGoToLoginClicked -> {
                sendUiEvent(
                    UiEvent.Navigate(
                        route = Routes.LOGIN_USER_SCREEN
                    )
                )
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