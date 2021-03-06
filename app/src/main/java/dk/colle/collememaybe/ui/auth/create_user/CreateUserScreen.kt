package dk.colle.collememaybe.ui.auth.create_user

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import dk.colle.collememaybe.ui.standardcomponents.AnimatedButton
import dk.colle.collememaybe.ui.standardcomponents.DatePickerOwn
import dk.colle.collememaybe.ui.standardcomponents.HeaderText
import dk.colle.collememaybe.ui.standardcomponents.InputField
import dk.colle.collememaybe.util.UiEvent
import kotlinx.coroutines.flow.collectLatest

@ExperimentalComposeUiApi
@Composable
fun CreateUserScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: CreateUserViewModel = hiltViewModel()
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
                        viewModel.onEvent(CreateUserEvent.OnGoToLoginClicked)
                    }
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Column(
            Modifier.fillMaxSize(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CreateUserInputs()
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun CreateUserInputs(viewModel: CreateUserViewModel = hiltViewModel()) {
    HeaderText(title = "Create user")
    InputField(
        label = "Enter name",
        onValueChange = { viewModel.onEvent(CreateUserEvent.OnEditName(name = it)) },
        textState = viewModel.name,
        leadIcon = Icons.Filled.PermIdentity,
        leadIconDesc = "Name icon"
    )
    DatePickerOwn(
        dateState = viewModel.birthday,
        onChangeDate = {
            viewModel.onEvent(CreateUserEvent.OnEditBirthday(birthday = it))
        })
    InputField(
        label = "Enter email",
        onValueChange = { viewModel.onEvent(CreateUserEvent.OnEditEmail(email = it)) },
        textState = viewModel.email,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        leadIcon = Icons.Filled.Email,
        leadIconDesc = "Email icon"
    )
    InputField(
        label = "Enter password",
        onValueChange = { viewModel.onEvent(CreateUserEvent.OnEditPassword(password = it)) },
        textState = viewModel.password,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        leadIcon = if (viewModel.showPasswordState.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
        leadIconDesc = "Password lock",
        leadIconClick = {
            viewModel.onEvent(
                CreateUserEvent.ToggleShowPassword
            )
        })
    InputField(
        label = "Enter phone number",
        onValueChange = { viewModel.onEvent(CreateUserEvent.OnEditPhoneNumber(phoneNumber = it)) },
        textState = viewModel.phoneNumber,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        leadIcon = Icons.Filled.Phone,
        leadIconDesc = "Phone icon"
    )
    AnimatedButton(
        buttonText = "Confirm",
        onClick = {
            viewModel.onEvent(
                CreateUserEvent.OnCreateUser
            )
        },
        isEnabled = { viewModel.confirmButtonClickable.value }
    )
}
