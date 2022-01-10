package dk.colle.collememaybe.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import dk.colle.collememaybe.ui.auth.create_user.CreateUserScreen
import dk.colle.collememaybe.ui.auth.login_user.LoginScreen
import dk.colle.collememaybe.ui.chat.ChatScreen
import dk.colle.collememaybe.ui.splashscreen.SplashScreenViewModel
import dk.colle.collememaybe.ui.startscreen.StartScreen
import dk.colle.collememaybe.util.Routes


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val splashScreenViewModel: SplashScreenViewModel by viewModels()
    private val TAG = "Main activity"

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            this.setKeepVisibleCondition {
                splashScreenViewModel.isLoading.value
            }
        }
        setContent {
            Log.d(
                TAG,
                "Setting compose content with current user email:${splashScreenViewModel.user.collectAsState().value?.email}, uid:${splashScreenViewModel.user.collectAsState().value?.uid}"
            )
            val navController = rememberNavController()
            // If the user is not logged in then we want to direct the user to the login screen else direct them to the normal start screen
            NavHost(
                navController = navController,
                startDestination = if (splashScreenViewModel.user.collectAsState().value == null) Routes.LOGIN_USER_SCREEN else Routes.START_SCREEN
            ) {

                // Screen that shows list of servers, friends and chats
                composable(
                    route = Routes.START_SCREEN
                ) {
                    StartScreen(onNavigate = {
                        navController.navigate(
                            it.route
                        ) {
                            launchSingleTop = true
                        }
                    })
                }

                // Screen for logging in as existing user
                composable(route = Routes.LOGIN_USER_SCREEN) {
                    LoginScreen(onNavigate = {
                        navController.navigate(it.route) {
                            launchSingleTop = true
                        }
                    })
                }

                // Screen for creating a new user
                composable(route = Routes.CREATE_USER_SCREEN) {
                    CreateUserScreen(
                        onNavigate = {
                            navController.navigate(it.route) {
                                launchSingleTop = true
                            }
                        }
                    )
                }

                // Screen for specific server
                composable(route = Routes.SERVER_SCREEN + "?serverId={serverId}",
                    arguments = listOf(
                        navArgument(name = "serverId") {
                            type = NavType.StringType
                            defaultValue = ""
                        }
                    )) {
                }

                // Screen for specific chat
                composable(route = Routes.CHAT_SCREEN + "/{chatId}",
                    arguments = listOf(
                        navArgument(name = "chatId") {
                            type = NavType.StringType
                        }
                    )) { backStackEntry ->
                    val chatId = backStackEntry.arguments?.getString("chatId")
                    Log.d(TAG, "Navigating to chat screen with id $chatId")
                    chatId?.let {
                        ChatScreen(
                            onNavigate = {
                                navController.navigate(
                                    it.route
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}