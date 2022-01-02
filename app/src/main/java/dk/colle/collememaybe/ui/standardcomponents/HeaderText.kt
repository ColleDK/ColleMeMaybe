package dk.colle.collememaybe.ui.standardcomponents

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dk.colle.collememaybe.ui.theme.textColor

@Composable
fun HeaderText(
    title: String,
    widthSize: Float = 1f,
    color: Color = MaterialTheme.colors.textColor,
    fontSize: Int = 20,
    maxLines: Int = 1,
    textAlign: TextAlign = TextAlign.Center,
    topPadding: Int = 20
) {
    Text(
        text = title,
        modifier = Modifier
            .fillMaxWidth(widthSize)
            .padding(top = topPadding.dp)
            .wrapContentHeight(align = Alignment.CenterVertically)
            .clip(CircleShape),
        textAlign = textAlign,
        fontWeight = FontWeight.Bold,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        color = color,
        fontSize = fontSize.sp,
    )
}