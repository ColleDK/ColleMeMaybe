package dk.colle.collememaybe.ui.standardcomponents
import android.app.Activity
import android.util.Log
import android.view.MotionEvent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import dk.colle.collememaybe.R
import dk.colle.collememaybe.ResultCommand
import dk.colle.collememaybe.ui.auth.InputTextState
import dk.colle.collememaybe.ui.theme.*

@Composable
fun HeaderText(
    title: String,
    widthSize: Float = 1f,
    color: Color=MaterialTheme.colors.textColor,
    fontSize: Int=20,
    maxLines: Int = 1,
    textAlign: TextAlign = TextAlign.Center
){
    Text(text = title,
        modifier = Modifier
            .fillMaxWidth(widthSize)
            .wrapContentHeight(align = CenterVertically)
            .clip(CircleShape),
        textAlign = textAlign,
        fontWeight = FontWeight.Bold,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        color = color,
        fontSize = fontSize.sp,
    )
}


@Composable
fun InputField(
    textState: InputTextState = remember{InputTextState()}, 
    label: String = "Enter text", 
    widthSize: Float = 0.7f,
    backgroundColor: Color=MaterialTheme.colors.inputTextBackground, 
    textColor: Color = MaterialTheme.colors.inputTextText, 
    fontSize: Int=12, 
    maxLines: Int = 2,
    textAlign: TextAlign = TextAlign.Left, 
    bold: Boolean = false, 
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text), 
    leadIconClick: () -> Unit = {}, 
    leadIcon: ImageVector? = null, 
    leadIconDesc: String = "",
    enableErrorIcon: Boolean = false
){
//    var input by remember { mutableStateOf(initialText) }
    TextField(
        value = textState.text,
        onValueChange = {textState.text = it },
        modifier = Modifier
            .fillMaxWidth(widthSize)
            .wrapContentHeight(align = CenterVertically)
            .border(
                BorderStroke(
                    width = 4.dp,
                    brush = Brush.horizontalGradient(
                        listOf(
                            Purple200,
                            Purple500
                        )
                    )
                ),
                shape = CircleShape
            )
            .clip(CircleShape),
        label = { Text(text = label)},
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = backgroundColor,
            textColor = textColor
        ),
        maxLines = maxLines,
        textStyle = TextStyle(
            fontSize = fontSize.sp,
            textAlign = textAlign,
            fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal
        ),
        keyboardOptions = keyboardOptions,
        leadingIcon = {
            if (leadIcon != null) {
                IconButton(onClick = { leadIconClick() }) {
                    Icon(
                        imageVector = leadIcon,
                        contentDescription = leadIconDesc
                    )
                }
            }
        },
        visualTransformation = if (leadIcon == Icons.Filled.Visibility) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = {
            if (enableErrorIcon) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Filled.Error,
                        contentDescription = "Input error"
                    )
                }
            }
        }
    )
}

@ExperimentalComposeUiApi
@Composable
fun AnimatedButton(
    buttonText: String,
    onClick: () -> Unit,
    scale1: Float = .8f,
    scale2: Float = 1f,
    buttonColor: Color = MaterialTheme.colors.buttonColor,
    textColor: Color = MaterialTheme.colors.textColor,
    textAlign: TextAlign = TextAlign.Center,
    maxLines: Int = 1,
    fontSize: Int = 12
){
    var clicked by remember {
        mutableStateOf(false)
    }

    val scale = animateFloatAsState(targetValue = if (clicked) scale1 else scale2)

    Column(Modifier.fillMaxWidth(1f), verticalArrangement = Arrangement.Center, horizontalAlignment = CenterHorizontally) {
        Button(onClick = {onClick()},
            modifier = Modifier
                .scale(scale = scale.value)
                .wrapContentWidth(CenterHorizontally)
                .wrapContentHeight(CenterVertically)
                .pointerInteropFilter {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                            clicked = true
                            Log.d("Button", "button clicked")
                            onClick()
                        }
                        MotionEvent.ACTION_UP -> {
                            clicked = false
                        }
                    }
                    true
                }
                .border(
                    BorderStroke(
                        width = 4.dp,
                        brush = Brush.horizontalGradient(
                            listOf(
                                Purple200,
                                Purple500
                            )
                        )
                    ),
                    shape = RoundedCornerShape(25)
                ),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = buttonColor
                ))
        {
            Text(
                text = buttonText,
                modifier = Modifier
                    .wrapContentWidth(CenterHorizontally)
                    .wrapContentHeight(align = CenterVertically),
                textAlign = textAlign,
                fontWeight = FontWeight.Bold,
                maxLines = maxLines,
                overflow = TextOverflow.Ellipsis,
                color = textColor,
                fontSize = fontSize.sp,
            )
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun AlertBox(
    status: ResultCommand.Status,
    message: String,
    openValue: () -> Boolean,
    onDismiss: () -> Unit
){
    // Check if the box needs to be opened
    if (openValue()){
        when(status){
            ResultCommand.Status.SUCCESS -> {
                val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.success))
                val activity = (LocalContext.current as Activity)
                AlertDialog(onDismissRequest = {
                    onDismiss()
                    // End the activity when you sold the crypto
                    activity.finish()},
                    properties = DialogProperties(usePlatformDefaultWidth = false),

                    title = {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxWidth(1f)) {
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
                                }, colors = ButtonDefaults.buttonColors(MaterialTheme.colors.buttonColor)
                            ) {
                                Text("Finish", color = Color.Black, style = TextStyle(fontWeight = FontWeight.Bold))
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
                            modifier = Modifier.fillMaxWidth(1f)) {
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
                                }, colors = ButtonDefaults.buttonColors(MaterialTheme.colors.buttonColor)
                            ) {
                                Text("Dismiss", color = Color.Black, style = TextStyle(fontWeight = FontWeight.Bold))
                            }
                        }
                    },
                    backgroundColor = Color.White
                )
            }
        }
    }

}