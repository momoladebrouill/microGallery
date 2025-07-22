package studio.lunabee.amicrogallery.android.core.ui.component.image

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.SubcomposeAsyncImage
import coil3.network.HttpException
import coil3.request.ErrorResult
import coil3.request.ImageRequest
import coil3.request.placeholder
import studio.lunabee.amicrogallery.android.core.ui.shimmerLoading
import studio.lunabee.amicrogallery.core.ui.R
import studio.lunabee.compose.core.LbcTextSpec
import studio.lunabee.microgallery.android.data.MicroPicture

@Composable
fun MicroGalleryImage(
    picture: MicroPicture,
    modifier: Modifier = Modifier,
    defaultToHighRes: Boolean = false,
    contentDescription: LbcTextSpec? = null,
    onState: ((AsyncImagePainter.State) -> Unit)? = null,
    contentScale: ContentScale = ContentScale.Fit,
    alignment: Alignment = Alignment.Center,
    colorFilter: ColorFilter? = null,
) {
    val context = LocalContext.current
    var currentInd by remember { mutableIntStateOf(0) }
    val urlsToTry = if (defaultToHighRes) picture.highResPaths else picture.lowResPaths
    val len = urlsToTry.size
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(context)
            .data(urlsToTry[currentInd])
            .placeholder(R.drawable.ic_launcher_foreground)
            .listener(
                onError = { _, result ->
                    if (currentInd + 1 < len && isConnectionError(result)) {
                        currentInd = currentInd + 1
                    }
                },
            )
            .build(),
        loading = {
            Box(modifier = Modifier.size(200.dp).shimmerLoading())
        },
        contentDescription = contentDescription?.string,
        modifier = modifier,
        alignment = alignment,
        contentScale = contentScale,
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
