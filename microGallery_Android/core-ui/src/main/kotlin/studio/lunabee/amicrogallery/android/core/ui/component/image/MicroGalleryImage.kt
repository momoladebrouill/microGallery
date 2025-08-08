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
import coil3.network.HttpException
import coil3.request.ErrorResult
import coil3.request.ImageRequest
import coil3.request.placeholder
import studio.lunabee.amicrogallery.core.ui.R
import studio.lunabee.compose.core.LbcTextSpec
import studio.lunabee.microgallery.android.data.MicroPicture
import studio.lunabee.microgallery.android.domain.photoviewer.UrlIndex

@Composable
fun MicroGalleryImage(
    picture: MicroPicture,
    modifier: Modifier = Modifier,
    currentInd: UrlIndex = UrlIndex(),
    defaultToHighRes: Boolean = false,
    contentDescription: LbcTextSpec? = null,
    onState: ((AsyncImagePainter.State) -> Unit)? = null,
    contentScale: ContentScale = ContentScale.Fit,
    alignment: Alignment = Alignment.Center,
    colorFilter: ColorFilter? = null,
    errorPainter: Painter? = null,
) {
    val context = LocalContext.current
    val urlsToTry = if (defaultToHighRes) picture.highResPaths else picture.lowResPaths
    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(urlsToTry[currentInd.get()])
            .placeholder(R.drawable.ic_launcher_foreground)
            .listener(
                onError = { _, result ->
                    if (currentInd + 1 < urlsToTry.size && isConnectionError(result)) {
                        currentInd.increment()
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

fun isConnectionError(result: ErrorResult): Boolean {
    return when (result.throwable) {
        is HttpException -> (result.throwable as HttpException).response.code == 404
        is java.net.ConnectException -> {
            val exception = result.throwable as java.net.ConnectException
            println(exception.message) // Failed to connect to
            true
        }

        else -> false
    }
}
