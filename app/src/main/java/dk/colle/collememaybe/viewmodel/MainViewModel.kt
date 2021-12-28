package dk.colle.collememaybe.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.colle.collememaybe.repository.BaseAuthRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
//    private val savedStateHandle: SavedStateHandle,
    private val repository: BaseAuthRepository
) : ViewModel(

) {
    private val TAG = "MainViewModel"


    private val _user = MutableStateFlow<FirebaseUser?>(null)
    val user = _user.asStateFlow()

    private val _events = Channel<AllEvents>()
    val events = _events.receiveAsFlow()


    fun createUser(email: String, password: String) = viewModelScope.launch {
        when {
            email.isEmpty() -> {
                _events.send(AllEvents.ErrorCode(1))
            }
            password.isEmpty() -> {
                _events.send(AllEvents.ErrorCode(2))
            }
            else -> {
                Log.d(TAG, "Signing up the user")
                signUpUser(email, password)
            }
        }
    }


    private fun signUpUser(email: String, password: String) = viewModelScope.launch {
        try {
            val firebaseUser = repository.signUpWithEmailPassword(email = email, password = password)
            firebaseUser?.let {
                _user.value = it
                _events.send(AllEvents.Message("User created"))
            }

        } catch (e: Exception){
            val error = e.toString().split(":").toTypedArray()
            Log.d(TAG, "Create user: ${error[1]}")
            _events.send(AllEvents.Error(error[1]))
        }
    }


    fun signOut() = viewModelScope.launch {
        try {
            repository.signOut()
            _events.send(AllEvents.Message("User signed out"))
            Log.d(TAG, "User signed out")
        } catch (e: Exception){
            val error = e.toString().split(":").toTypedArray()
            Log.d(TAG, "Create user: ${error[1]}")
            _events.send(AllEvents.Error(error[1]))
        }
    }




    sealed class AllEvents {
        data class Message(val message : String) : AllEvents()
        data class ErrorCode(val code : Int):AllEvents()
        data class Error(val error : String) : AllEvents()
    }
}