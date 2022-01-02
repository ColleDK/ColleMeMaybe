package dk.colle.collememaybe.ui.standardcomponents

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import dk.colle.collememaybe.R
import dk.colle.collememaybe.util.ResultCommand
import dk.colle.collememaybe.ui.theme.buttonColor

@ExperimentalComposeUiApi
@Composable
fun AlertBox(
    status: ResultCommand.Status,
    message: String,
    openValue: () -> Boolean,
    onDismiss: () -> Unit
) {
    // Check if the box needs to be opened
    if (openValue()) {
        when (status) {
            ResultCommand.Status.SUCCESS -> {
                val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.success))
                val activity = (LocalContext.current as Activity)
                AlertDialog(onDismissRequest = {
                    onDismiss()
                    // End the activity when you sold the crypto
                    activity.finish()
                },
                    properties = DialogProperties(usePlatformDefaultWidth = false),

                    title = {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxWidth(1f)
                        ) {
                            LottieAnimation(composition, modifier = Modifier.size(200.dp))
                        }
                    }, text = {
                        Text(
                            text = message,
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                            style = TextStyle(fontWeight = FontWeight.Bold),
                            modifier = Modifier.fillMaxWidth(1f)
                        )
                    },
                    buttons = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .padding(all = 8.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                onClick = {
                                    onDismiss()
                                    // End the activity when you sold the crypto
                                    activity.finish()
                                },
                                colors = ButtonDefaults.buttonColors(MaterialTheme.colors.buttonColor)
                            ) {
                                Text(
                                    "Finish",
                                    color = Color.Black,
                                    style = TextStyle(fontWeight = FontWeight.Bold)
                                )
                            }
                        }
                    },
                    backgroundColor = Color.White
                )
            }
            ResultCommand.Status.LOADING -> {

            }
            ResultCommand.Status.ERROR -> {
                val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.error))
                AlertDialog(onDismissRequest = { onDismiss() },
                    properties = DialogProperties(usePlatformDefaultWidth = false),

                    title = {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxWidth(1f)
                        ) {
                            LottieAnimation(composition, modifier = Modifier.size(200.dp))
                        }
                    }, text = {
                        Text(
                            text = message,
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                            style = TextStyle(fontWeight = FontWeight.Bold),
                            modifier = Modifier.fillMaxWidth(1f)
                        )
                    },
                    buttons = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .padding(all = 8.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                onClick = {
                                    onDismiss()
                                },
                                colors = ButtonDefaults.buttonColors(MaterialTheme.colors.buttonColor)
                            ) {
                                Text(
                                    "Dismiss",
                                    color = Color.Black,
                                    style = TextStyle(fontWeight = FontWeight.Bold)
                                )
                            }
                        }
                    },
                    backgroundColor = Color.White
                )
            }
        }
    }
}