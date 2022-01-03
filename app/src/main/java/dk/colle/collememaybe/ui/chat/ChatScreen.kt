package dk.colle.collememaybe.ui.chat

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import dk.colle.collememaybe.util.UiEvent

@Composable
fun ChatScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: ChatScreenViewModel = hiltViewModel()
) {



}