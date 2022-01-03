package dk.colle.collememaybe.ui.startscreen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import dk.colle.collememaybe.ui.standardcomponents.AnimatedButton
import dk.colle.collememaybe.util.Routes
import dk.colle.collememaybe.util.UiEvent


@ExperimentalComposeUiApi
@Composable
fun StartScreen(onNavigate: (UiEvent.Navigate) -> Unit) {
    Column(Modifier.fillMaxSize(1f)) {
        AnimatedButton(buttonText = "Go to login screen", onClick = {
            Log.d("button", "navigating to login screen")
            onNavigate(UiEvent.Navigate(Routes.LOGIN_USER_SCREEN))
        })
    }
}