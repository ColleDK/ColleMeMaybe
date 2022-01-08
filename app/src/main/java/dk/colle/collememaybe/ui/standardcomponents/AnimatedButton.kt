package dk.colle.collememaybe.ui.standardcomponents

import android.view.MotionEvent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dk.colle.collememaybe.ui.theme.Purple200
import dk.colle.collememaybe.ui.theme.Purple500
import dk.colle.collememaybe.ui.theme.buttonColor
import dk.colle.collememaybe.ui.theme.textColor

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
    fontSize: Int = 12,
    isEnabled: () -> Boolean = { true }
) {
    var clicked by remember {
        mutableStateOf(false)
    }

    val scale = animateFloatAsState(targetValue = if (clicked) scale1 else scale2)

    Column(
        Modifier
            .fillMaxWidth(1f)
            .padding(bottom = 15.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { onClick() },
            modifier = Modifier
                .scale(scale = scale.value)
                .wrapContentWidth(Alignment.CenterHorizontally)
                .wrapContentHeight(Alignment.CenterVertically)
                .pointerInteropFilter {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                            clicked = true
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
            ),
            enabled = isEnabled()
        )
        {
            Text(
                text = buttonText,
                modifier = Modifier
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .wrapContentHeight(align = Alignment.CenterVertically),
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