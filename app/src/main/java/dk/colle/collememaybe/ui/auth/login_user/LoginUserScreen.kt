package dk.colle.collememaybe.ui.auth.login_user

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dk.colle.collememaybe.ui.auth.create_user.CreateUserEvent
import dk.colle.collememaybe.ui.standardcomponents.AnimatedButton
import dk.colle.collememaybe.ui.standardcomponents.HeaderText
import dk.colle.collememaybe.ui.standardcomponents.InputField
import dk.colle.collememaybe.util.Routes
import dk.colle.collememaybe.util.UiEvent
import kotlinx.coroutines.flow.collectLatest

@ExperimentalComposeUiApi
@Composable
fun LoginScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: LoginUserViewModel = hiltViewModel()
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
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Column(Modifier.fillMaxSize(1f)) {
            LoginHeader()
            LoginMiddle(onNavigate = onNavigate)
        }
    }

}

@Composable
fun LoginHeader() {
    Row(
        Modifier
            .fillMaxWidth(1f)
            .padding(top = 15.dp)
            .wrapContentHeight(align = CenterVertically)
    ) {
        HeaderText(title = "Login screen", fontSize = 30)
    }
}

@ExperimentalComposeUiApi
@Composable
fun LoginMiddle(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: LoginUserViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth(1f)
                .height(15.dp)
        )
        InputField(
            textState = viewModel.email,
            label = "Enter email",
            onValueChange = { viewModel.onEvent(LoginUserEvent.OnEditEmail(it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            leadIcon = Icons.Filled.Email,
            leadIconDesc = "Email icon"
        )
        InputField(
            textState = viewModel.password,
            label = "Enter password",
            onValueChange = { viewModel.onEvent(LoginUserEvent.OnEditPassword(it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            leadIcon = if (viewModel.showPasswordState.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
            leadIconDesc = "Password lock",
            leadIconClick = {
                viewModel.onEvent(
                    LoginUserEvent.ToggleShowPassword
                )
            }
        )
        AnimatedButton(
            buttonText = "Log in",
            onClick = { viewModel.onEvent(LoginUserEvent.OnLoginUser) })
        AnimatedButton(
            buttonText = "Create account",
            onClick = { onNavigate(UiEvent.Navigate(Routes.CREATE_USER_SCREEN)) })
    }
}
