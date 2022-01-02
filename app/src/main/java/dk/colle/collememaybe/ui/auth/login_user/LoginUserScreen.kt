package dk.colle.collememaybe.ui.auth.login_user

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dk.colle.collememaybe.ui.standardcomponents.AnimatedButton
import dk.colle.collememaybe.ui.standardcomponents.HeaderText
import dk.colle.collememaybe.util.Routes
import dk.colle.collememaybe.util.UiEvent

@ExperimentalComposeUiApi
@Composable
fun LoginScreen(onNavigate: (UiEvent.Navigate) -> Unit) {
    Column(Modifier.fillMaxSize(1f)) {
        LoginHeader()
        LoginMiddle(onNavigate = onNavigate)
    }
}

@Composable
fun LoginHeader() {
    Row(
        Modifier
            .fillMaxWidth(1f)
            .wrapContentHeight(align = CenterVertically)
    ) {
        HeaderText(title = "Login screen")
    }
}

@ExperimentalComposeUiApi
@Composable
fun LoginMiddle(onNavigate: (UiEvent.Navigate) -> Unit) {
    Column(
        Modifier
            .fillMaxWidth(1f)
            .wrapContentHeight(align = Alignment.Top)
    ) {
        Spacer(modifier = Modifier
            .fillMaxWidth(1f)
            .height(15.dp))
        AnimatedButton(buttonText = "Create account", onClick = { onNavigate(UiEvent.Navigate(Routes.CREATE_USER)) })
        Spacer(modifier = Modifier
            .fillMaxWidth(1f)
            .height(15.dp))
        AnimatedButton(buttonText = "Log in", onClick = {})
    }
}
