package studio.lunabee.amicrogallery.android.core.ui.component.image

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.network.HttpException
import coil3.request.ImageRequest
import coil3.request.placeholder
import studio.lunabee.amicrogallery.core.ui.R
import studio.lunabee.compose.core.LbcTextSpec
import studio.lunabee.microgallery.android.data.Picture

@Composable
fun MicroGalleryImage(
    picture: Picture,
    modifier: Modifier = Modifier,
    defaultToHighRes: Boolean = false,
    contentDescription: LbcTextSpec? = null,
    onState: ((AsyncImagePainter.State) -> Unit)? = null,
    contentScale: ContentScale = ContentScale.Fit,
    alignment: Alignment = Alignment.Center,
    colorFilter: ColorFilter? = null,
    errorPainter: Painter? = null,
) {
    val (url, fallBackUrl) =
        if (defaultToHighRes) {
            Pair("http://92.150.239.130" + picture.fullResPath, "http://92.150.239.130" + picture.lowResPath)
        } else {
            Pair("http://92.150.239.130" + picture.lowResPath, "http://92.150.239.130" + picture.fullResPath)
        }

    var triedFallback by remember { mutableStateOf(false) }
    var currentUrl by remember { mutableStateOf(url) }
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(currentUrl)
            .placeholder(R.drawable.ic_launcher_foreground)
            .listener(
                onError = { _, result ->

                    if (!triedFallback && result.throwable is HttpException) {
                        val exception = result.throwable as HttpException

                        if (exception.response.code == 404) {
                            triedFallback = true
                            currentUrl = fallBackUrl
                        }
                    }
                },
            )
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
