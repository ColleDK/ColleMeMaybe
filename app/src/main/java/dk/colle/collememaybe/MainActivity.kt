package dk.colle.collememaybe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dk.colle.collememaybe.ui.theme.ColleMeMaybeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ColleMeMaybeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    StartScreen()
                }
            }
        }
    }
}

@Composable
fun StartScreen(){

}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ColleMeMaybeTheme {
        StartScreen()
    }
}