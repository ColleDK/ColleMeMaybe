package dk.colle.collememaybe.ui.startscreen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dk.colle.collememaybe.model.ServerModel
import dk.colle.collememaybe.ui.standardcomponents.AnimatedButton
import dk.colle.collememaybe.ui.standardcomponents.ImageUri
import dk.colle.collememaybe.util.Routes
import dk.colle.collememaybe.util.UiEvent


@ExperimentalComposeUiApi
@Composable
fun StartScreen(onNavigate: (UiEvent.Navigate) -> Unit) {
    Column(Modifier.fillMaxSize(1f)) {
        AnimatedButton(buttonText = "Go to chat", onClick = {
            Log.d("button", "navigating to login screen")
            onNavigate(UiEvent.Navigate("${Routes.CHAT_SCREEN}/thd2pTXuApXaMc42Yy0A"))
        })
    }
}

@Composable
fun StartScreenServerList(viewmodel: StartScreenViewModel = hiltViewModel()) {
    val servers = viewmodel.servers.collectAsState().value
    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.wrapContentSize(
//            align = Alignment.TopStart
        )
    ) {
        items(servers) { server ->
            StartScreenServerItem(server = server)
        }
    }
}


@Composable
fun StartScreenServerItem(server: ServerModel) {
    server.serverPicUri?.let {
        ImageUri(
            uri = it,
            modifier = Modifier
                .size(64.dp)
                .wrapContentSize()
                .padding(5.dp)
                .clip(CircleShape)
        )
    }
}