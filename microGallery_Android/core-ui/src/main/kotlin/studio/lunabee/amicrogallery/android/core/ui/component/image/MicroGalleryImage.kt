package studio.lunabee.amicrogallery.android.core.ui.component.image

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.request.ImageRequest
import studio.lunabee.compose.core.LbcTextSpec

@Composable
fun MicroGalleryImage(
    url: String,
    modifier: Modifier = Modifier,
    contentDescription: LbcTextSpec? = null,
    onState: ((AsyncImagePainter.State) -> Unit)? = null,
    contentScale: ContentScale = ContentScale.Fit,
    alignment: Alignment = Alignment.Center,
    colorFilter: ColorFilter? = null,
    errorPainter: Painter? = null,
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .build(),
        contentDescription = contentDescription?.string,
        modifier = modifier,
        alignment = alignment,
        contentScale = contentScale,
        error = errorPainter,
        onError = onState,
        onLoading = onState,
        onSuccess = onState,
        colorFilter = colorFilter,
    )
}
