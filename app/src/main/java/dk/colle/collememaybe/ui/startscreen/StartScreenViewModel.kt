package dk.colle.collememaybe.ui.startscreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dk.colle.collememaybe.repository.auth.BaseAuthRepository
import javax.inject.Inject

@HiltViewModel
class StartScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    repository: BaseAuthRepository
) : ViewModel() {


}