package dk.colle.collememaybe.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import dk.colle.collememaybe.ui.standardcomponents.AlertBox
import dk.colle.collememaybe.ui.standardcomponents.AnimatedButton
import dk.colle.collememaybe.ui.standardcomponents.InputField
import dk.colle.collememaybe.viewmodel.AuthViewModel

class createUser_frag : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext().apply {

        })
    }
}


@ExperimentalComposeUiApi
@Composable
fun CreateUserScreen(navController: NavController, viewModel: AuthViewModel){
    Column(Modifier.fillMaxSize(1f), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        CreateUserHeader()
        CreateUserInputs(viewModel = viewModel)
        ObserveAlertCreate(viewModel = viewModel)
    }
}

@Composable
fun CreateUserHeader(){
    
}

@ExperimentalComposeUiApi
@Composable
fun CreateUserInputs(viewModel: AuthViewModel){
    val nameState = remember {
        InputTextState()
    }

    val ageState = remember {
        InputTextState()
    }

    val emailState = remember {
        InputTextState()
    }

    val passwordState = remember {
        InputTextState()
    }

    val passwordClickState = remember {
        mutableStateOf(true)
    }

    val phoneState = remember {
        InputTextState()
    }

    InputField(label = "Enter name", textState = nameState, leadIcon = Icons.Filled.PermIdentity, leadIconDesc = "Name icon")
    Spacer(modifier = Modifier
        .fillMaxWidth(1f)
        .height(15.dp))
    InputField(label = "Enter age", textState = ageState, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), leadIcon = Icons.Filled.CalendarToday, leadIconDesc = "Age icon")
    Spacer(modifier = Modifier
        .fillMaxWidth(1f)
        .height(15.dp))
    InputField(label = "Enter email", textState = emailState , keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email), leadIcon = Icons.Filled.Email, leadIconDesc = "Email icon")
    Spacer(modifier = Modifier
        .fillMaxWidth(1f)
        .height(15.dp))
    InputField(label = "Enter password", textState = passwordState , keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password), leadIcon = if (passwordClickState.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff, leadIconDesc = "Password lock", leadIconClick = {passwordClickState.value = !passwordClickState.value})
    Spacer(modifier = Modifier
        .fillMaxWidth(1f)
        .height(15.dp))
    InputField(label = "Enter phone number", textState = phoneState , keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone), leadIcon = Icons.Filled.Phone, leadIconDesc = "Phone icon")
    Spacer(modifier = Modifier
        .fillMaxWidth(1f)
        .height(15.dp))
    AnimatedButton(buttonText = "Confirm", onClick = { viewModel.checkInputs(name = nameState.text, age = ageState.text, email = emailState.text, password = passwordState.text, phoneNumber = phoneState.text) })
}

@ExperimentalComposeUiApi
@Composable
fun ObserveAlertCreate(viewModel: AuthViewModel){
    // Dismissing the dialog
    val openDialog = remember { mutableStateOf(true)}
    // Observable state for the error handling
    val error = viewModel.events.collectAsState(initial = null)

    // Reset the dialog value when
    openDialog.value = true

    // Pass the error to the alert box
    error.value?.message?.let { AlertBox(status = error.value!!.status, message = it, openValue = {openDialog.value}, onDismiss = {
        openDialog.value = false
    }) }
}


class InputTextState(){
    var text: String by mutableStateOf("")
}
