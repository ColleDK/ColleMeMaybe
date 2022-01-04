package dk.colle.collememaybe.ui.standardcomponents

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
import dk.colle.collememaybe.R

@Composable
fun GlideImageUri(
    uri: Uri
) {
    GlideImage(
        imageModel = uri,
        contentScale = ContentScale.Crop,
        circularReveal = CircularReveal(duration = 250),
        placeHolder = ImageBitmap.imageResource(R.drawable.ic_baseline_person_24),
        error = ImageBitmap.imageResource(R.drawable.ic_baseline_error_24)
    )

}