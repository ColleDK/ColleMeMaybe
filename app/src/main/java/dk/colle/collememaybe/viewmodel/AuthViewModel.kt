package dk.colle.collememaybe.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.colle.collememaybe.ResultCommand
import dk.colle.collememaybe.repository.BaseAuthRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: BaseAuthRepository
) : ViewModel(

) {
    private val TAG = "MainViewModel"

    private val _user = MutableStateFlow<FirebaseUser?>(null)
    val user = _user.asStateFlow()

    private val _events = Channel<ResultCommand>()
    val events = _events.receiveAsFlow()


    private fun createUser(name: String, age: String, email: String, password: String, phoneNumber: String) = viewModelScope.launch {
        Log.d(TAG, "Signing up the user")
        signUpUser(email, password)
    }

    private fun signUpUser(email: String, password: String) = viewModelScope.launch {
        try {
            val firebaseUser = repository.signUpWithEmailPassword(email = email, password = password)
            firebaseUser?.let {
                _user.value = it
                _events.send(ResultCommand("User created successfully", ResultCommand.Status.SUCCESS))
            }

        } catch (e: Exception){
            val error = e.toString().split(":").toTypedArray()
            Log.d(TAG, "Create user: ${error[1]}")
            _events.send(ResultCommand(error[1], ResultCommand.Status.ERROR))
        }
    }



    fun loginUser(email: String, password: String) = viewModelScope.launch {
        when {
            email.isEmpty() -> {
                _events.send(ResultCommand("Email cannot be empty", ResultCommand.Status.ERROR))
            }
            password.isEmpty() -> {
                _events.send(ResultCommand("Password cannot be empty", ResultCommand.Status.ERROR))
            }
            else -> {
                Log.d(TAG, "Signing up the user")
                signUpUser(email, password)
            }
        }
    }

    private fun loginUserOwn(email: String, password: String) = viewModelScope.launch {
        try {
            val firebaseUser = repository.signInWithEmailPassword(email, password)
            firebaseUser?.let {
                Log.d(TAG, "User logged in with id ${it.uid} and email ${it.email}")
                _user.value = it
                _events.send(ResultCommand("User logged in successfully", ResultCommand.Status.SUCCESS))
            }
        } catch (e: Exception){
            val error = e.toString().split(":").toTypedArray()
            Log.d(TAG, "Create user: ${error[1]}")
            _events.send(ResultCommand(error[1], ResultCommand.Status.ERROR))
        }
    }



    fun signOut() = viewModelScope.launch {
        try {
            repository.signOut()
            _events.send(ResultCommand("User signed out successfully", ResultCommand.Status.SUCCESS))
            Log.d(TAG, "User signed out")
        } catch (e: Exception){
            val error = e.toString().split(":").toTypedArray()
            Log.d(TAG, "Create user: ${error[1]}")
            _events.send(ResultCommand(error[1], ResultCommand.Status.ERROR))
        }
    }

    fun checkInputs(name: String, age: String, email: String, password: String, phoneNumber: String) = viewModelScope.launch {
        _events.send(ResultCommand("Creating user", ResultCommand.Status.LOADING))
        // check empty input
        var isError = false
        when {
            name.isEmpty() -> {
                _events.send(ResultCommand("Name cannot be empty", ResultCommand.Status.ERROR))
                isError = true
            }
            age.isEmpty() -> {
                _events.send(ResultCommand("Age cannot be empty", ResultCommand.Status.ERROR))
                isError = true
            }
            email.isEmpty() -> {
                _events.send(ResultCommand("Email cannot be empty", ResultCommand.Status.ERROR))
                isError = true
            }
            password.isEmpty() -> {
                _events.send(ResultCommand("Password cannot be empty", ResultCommand.Status.ERROR))
                isError = true
            }
            phoneNumber.isEmpty() -> {
                _events.send(
                    ResultCommand(
                        "Phone number cannot be empty",
                        ResultCommand.Status.ERROR
                    )
                )
                isError = true
            }
        }
        // if empty we break the job
        if (isError) viewModelScope.coroutineContext.job.cancel()

        try {
            // Create error if the age is not plausible
            if (age.toInt() < 0 || age.toInt() > 100) {
                _events.send(ResultCommand("Age is not in range (0 - 100)", ResultCommand.Status.ERROR))
            }

        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
            _events.send(ResultCommand("Age or phone number is not exclusively numbers", ResultCommand.Status.ERROR))
        }
    }



}