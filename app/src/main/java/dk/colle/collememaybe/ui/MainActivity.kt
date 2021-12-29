package dk.colle.collememaybe.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dk.colle.collememaybe.ui.auth.CreateUserScreen
import dk.colle.collememaybe.ui.auth.LoginScreen
import dk.colle.collememaybe.ui.standardcomponents.AnimatedButton
import dk.colle.collememaybe.viewmodel.AuthViewModel


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "startScreen"){
                composable("startScreen"){ StartScreen(navController = navController)}
                composable("loginScreen"){ LoginScreen(navController = navController)}
                composable("createUserScreen"){ CreateUserScreen(navController = navController, viewModel = authViewModel) }
            }
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun StartScreen(navController: NavController) {
    Column(Modifier.fillMaxSize(1f)) {
        AnimatedButton(buttonText = "Go to login screen", onClick = {
            Log.d("button", "navigating to login screen")
            navController.navigate("loginScreen")
        })
    }
}