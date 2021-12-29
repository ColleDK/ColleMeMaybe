package dk.colle.collememaybe.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import dagger.hilt.android.AndroidEntryPoint
import dk.colle.collememaybe.ui.standardcomponents.AnimatedButton
import dk.colle.collememaybe.ui.standardcomponents.HeaderText
import dk.colle.collememaybe.viewmodel.AuthViewModel

@AndroidEntryPoint
class login_frag : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

            }
        }
    }
}


@ExperimentalComposeUiApi
@Composable
fun LoginScreen(navController: NavController){
    Column(Modifier.fillMaxSize(1f)) {
        LoginHeader()
        LoginMiddle(navController = navController)
    }
}

@Composable
fun LoginHeader(){
    Row(
        Modifier
            .fillMaxWidth(1f)
            .wrapContentHeight(align = CenterVertically)) {
        HeaderText(title = "Login screen")
    }
}

@ExperimentalComposeUiApi
@Composable
fun LoginMiddle(navController: NavController){
    Column(
        Modifier
            .fillMaxWidth(1f)
            .wrapContentHeight(align = Alignment.Top)) {
        Spacer(modifier = Modifier.fillMaxWidth(1f).height(15.dp))
        AnimatedButton(buttonText = "Create account", onClick = {navController.navigate("createUserScreen")})
        Spacer(modifier = Modifier.fillMaxWidth(1f).height(15.dp))
        AnimatedButton(buttonText = "Log in", onClick = {})
    }
}
