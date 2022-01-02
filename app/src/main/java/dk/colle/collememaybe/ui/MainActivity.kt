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
import dk.colle.collememaybe.ui.auth.create_user.CreateUserScreen
import dk.colle.collememaybe.ui.auth.login_user.LoginScreen
import dk.colle.collememaybe.ui.standardcomponents.AnimatedButton
import dk.colle.collememaybe.util.Routes
import dk.colle.collememaybe.util.UiEvent
import dk.colle.collememaybe.viewmodel.CreateUserViewModel


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val createUserViewModel: CreateUserViewModel by viewModels()

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Routes.START_SCREEN) {
                composable(route = Routes.START_SCREEN) {
                    StartScreen(onNavigate = {
                        navController.navigate(
                            it.route
                        )
                    })
                }
                composable(route = Routes.LOGIN_USER) {
                    LoginScreen(onNavigate = {
                        navController.navigate(it.route)
                    })
                }
                composable(route = Routes.CREATE_USER) {
                    CreateUserScreen(
                        onNavigate = { navController.navigate(it.route) },
                        viewModel = createUserViewModel
                    )
                }
            }
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun StartScreen(onNavigate: (UiEvent.Navigate) -> Unit) {
    Column(Modifier.fillMaxSize(1f)) {
        AnimatedButton(buttonText = "Go to login screen", onClick = {
            Log.d("button", "navigating to login screen")
            onNavigate(UiEvent.Navigate(Routes.LOGIN_USER))
        })
    }
}