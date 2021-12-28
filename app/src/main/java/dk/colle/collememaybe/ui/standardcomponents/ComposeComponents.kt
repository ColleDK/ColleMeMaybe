package dk.colle.collememaybe.ui.standardcomponents

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import dk.colle.collememaybe.ui.theme.headerColor

@Composable
fun HeaderText(title: String, widthSize: Float = 1f, color: Color=MaterialTheme.colors.headerColor, fontSize: Int=20, maxLines: Int = 1, textAlign: TextAlign = TextAlign.Center){
    Text(text = title,
        modifier = Modifier.fillMaxWidth(widthSize),
        textAlign = textAlign,
        fontWeight = FontWeight.Bold,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        color = color,
        fontSize = fontSize.sp
    )
}