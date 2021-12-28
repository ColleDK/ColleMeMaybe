package dk.colle.collememaybe.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import dk.colle.collememaybe.ui.auth.LoginScreen
import dk.colle.collememaybe.ui.standardcomponents.HeaderText
import dk.colle.collememaybe.ui.theme.ColleMeMaybeTheme
import dk.colle.collememaybe.viewmodel.MainViewModel


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "startscreen"){
                composable("startscreen"){ StartScreen(navController = navController, viewmodel = mainViewModel)}
                composable("loginScreen"){ LoginScreen(navController = navController)}
            }
        }
    }
}

@Composable
fun StartScreen(navController: NavController, viewmodel: MainViewModel) {
    val currentUser = viewmodel.user.collectAsState()

    Column(Modifier.fillMaxSize(1f)) {
        Button(onClick = {
            Log.d("main activity", "Button clicked")
            viewmodel.createUser(
                "williamdk@live.dk",
                "29i312mklefsal23910feadx"
            )
        }, modifier = Modifier.wrapContentSize(Alignment.Center)) {
            HeaderText(title = "Welcome ${currentUser.value?.email}")
        }
    }
}