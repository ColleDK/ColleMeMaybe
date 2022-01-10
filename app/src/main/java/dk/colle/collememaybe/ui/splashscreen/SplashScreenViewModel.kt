package dk.colle.collememaybe.ui.splashscreen

import android.annotation.SuppressLint
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.colle.collememaybe.repository.auth.BaseAuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: BaseAuthRepository
) : ViewModel() {

    private val _isLoading = mutableStateOf(true)
    val isLoading = _isLoading

    private val _user = MutableStateFlow<FirebaseUser?>(null)
    val user = _user.asStateFlow()

    init {
        _isLoading.value = true
        retrieveCurrentUser()
    }

    private fun retrieveCurrentUser() = viewModelScope.launch {
        _user.value = repository.getCurrentUser()
        isLoading.value = false
    }
}