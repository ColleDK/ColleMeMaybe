package dk.colle.collememaybe.ui.standardcomponents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dk.colle.collememaybe.ui.theme.Purple200
import dk.colle.collememaybe.ui.theme.Purple500
import dk.colle.collememaybe.ui.theme.inputTextBackground
import dk.colle.collememaybe.ui.theme.inputTextText

@Composable
fun InputField(
    textState: MutableState<String> = remember { mutableStateOf("") },
    onValueChange: (String) -> Unit = {textState.value = it},
    label: String = "Enter text",
    widthSize: Float = 0.7f,
    backgroundColor: Color = MaterialTheme.colors.inputTextBackground,
    textColor: Color = MaterialTheme.colors.inputTextText,
    fontSize: Int = 12,
    maxLines: Int = 2,
    textAlign: TextAlign = TextAlign.Left,
    bold: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    leadIconClick: () -> Unit = {},
    leadIcon: ImageVector? = null,
    leadIconDesc: String = "",
    enableErrorIcon: Boolean = false,
    bottomPadding: Int = 15
) {
    TextField(
        value = textState.value,
        onValueChange = { onValueChange(it) },
        modifier = Modifier
            .fillMaxWidth(widthSize)
            .wrapContentHeight(align = Alignment.CenterVertically)
            .padding(bottom = bottomPadding.dp)
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
        label = { Text(text = label) },
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