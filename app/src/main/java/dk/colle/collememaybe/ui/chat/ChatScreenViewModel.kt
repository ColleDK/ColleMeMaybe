package dk.colle.collememaybe.ui.chat

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.colle.collememaybe.repository.BaseAuthRepository
import javax.inject.Inject

@HiltViewModel
class ChatScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    repository: BaseAuthRepository
) : ViewModel() {

    init {

    }


}