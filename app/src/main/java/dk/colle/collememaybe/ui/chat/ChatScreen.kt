package dk.colle.collememaybe.ui.chat

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dk.colle.collememaybe.dto.MessageDto
import dk.colle.collememaybe.ui.standardcomponents.HeaderText
import dk.colle.collememaybe.ui.standardcomponents.ImageUri
import dk.colle.collememaybe.util.UiEvent
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ChatScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: ChatScreenViewModel = hiltViewModel()
) {

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collectLatest { event ->
            Log.d("Create user", "Received new event $event")
            when (event) {
                is UiEvent.Navigate -> {
                    onNavigate(event)
                }
                is UiEvent.ShowSnackbar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(ChatEvent.OnUndo)
                    }
                }
            }
        }
    }

    ChatList()
}


@Composable
fun ChatList(viewModel: ChatScreenViewModel = hiltViewModel()) {
    viewModel.chat.collectAsState().value?.let { chatDto ->
        LazyColumn(modifier = Modifier.fillMaxSize(1f)) {
            items(chatDto.messages) { message ->
                viewModel.getSenderUser(message.senderId)
                ChatItem(message = message)
            }
        }
    }
}

@Composable
fun ChatItem(message: MessageDto, viewModel: ChatScreenViewModel = hiltViewModel()) {
    Box(modifier = Modifier.fillMaxWidth(1f)) {
        Row(modifier = Modifier.fillMaxWidth(1f), verticalAlignment = Alignment.CenterVertically) {
            viewModel.senders.collectAsState().value[message.senderId]?.let { sender ->
                sender.profilePic?.let { profilePic ->
                    ImageUri(
                        uri = profilePic,
                        modifier = Modifier
                            .wrapContentSize(align = Alignment.Center)
                            .clip(RoundedCornerShape(10.dp))
                            .padding(15.dp)
                    )
                }
            }
            Column {
                viewModel.senders.collectAsState().value[message.senderId]?.let {
                    HeaderText(title = it.name)
                }
                if (message.message != null) {
                    Text(text = message.message)
                } else if (message.picture != null) {
                    ImageUri(uri = message.picture, modifier = Modifier)
                }
            }
        }
    }
}