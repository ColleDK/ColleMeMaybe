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
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dk.colle.collememaybe.ui.auth.create_user.CreateUserScreen
import dk.colle.collememaybe.ui.auth.login_user.LoginScreen
import dk.colle.collememaybe.ui.standardcomponents.AnimatedButton
import dk.colle.collememaybe.util.Routes
import dk.colle.collememaybe.util.UiEvent
import dk.colle.collememaybe.ui.splashscreen.SplashScreenViewModel


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val splashScreenViewModel: SplashScreenViewModel by viewModels()
    private val TAG = "Main activity"

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            this.setKeepVisibleCondition{
                splashScreenViewModel.isLoading.value
            }
        }
        setContent {
            Log.d(TAG, "Setting compose content with current user email:${splashScreenViewModel.user.value?.email}, uid:${splashScreenViewModel.user.value?.uid}")
            val navController = rememberNavController()
            // If the user is not logged in then we want to direct the user to the login screen else direct them to the normal start screen
            NavHost(navController = navController, startDestination = if (splashScreenViewModel.user.value == null) Routes.LOGIN_USER_SCREEN else Routes.START_SCREEN) {
                composable(route = Routes.START_SCREEN) {
                    StartScreen(onNavigate = {
                        navController.navigate(
                            it.route
                        )
                    })
                }
                composable(route = Routes.LOGIN_USER_SCREEN) {
                    LoginScreen(onNavigate = {
                        navController.navigate(it.route)
                    })
                }
                composable(route = Routes.CREATE_USER_SCREEN) {
                    CreateUserScreen(
                        onNavigate = { navController.navigate(it.route) }
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
            onNavigate(UiEvent.Navigate(Routes.LOGIN_USER_SCREEN))
        })
    }
}