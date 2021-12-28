package dk.colle.collememaybe.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import dagger.hilt.android.AndroidEntryPoint
import dk.colle.collememaybe.ui.standardcomponents.HeaderText
import dk.colle.collememaybe.ui.theme.ColleMeMaybeTheme

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


@Composable
fun LoginScreen(navController: NavController){
    Column(Modifier.fillMaxSize(1f)) {
        Button(onClick = {
            navController.navigate("startscreen") {
                launchSingleTop = true
            }
        }, modifier = Modifier.wrapContentSize(Alignment.Center)) {
            HeaderText(title = "Go to start")
        }
    }
}


