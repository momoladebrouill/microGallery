package studio.lunabee.amicrogallery.android.core.ui.component.image

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import org.koin.java.KoinJavaComponent.inject
import studio.lunabee.amicrogallery.core.ui.R
import studio.lunabee.compose.core.LbcTextSpec
import studio.lunabee.microgallery.android.data.Picture
import studio.lunabee.microgallery.android.domain.settings.SettingsRepository

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
    val settingsRepository : SettingsRepository by inject(SettingsRepository::class.java)
    val context = LocalContext.current
    val data = settingsRepository.settingsData
    val (begin0, begin1) =
        if(data.useIpv6)
            Pair(data.ipv6, data.ipv4)
        else
            Pair(data.ipv4, data.ipv6)
    val urlsToTry =
        if(data.viewInHD){
            val (end0, end1) =
                if (defaultToHighRes)
                    Pair(picture.fullResPath, picture.lowResPath)
                else
                    Pair( picture.lowResPath, picture.fullResPath)
            listOf(
                "$begin0/$end0",
                "$begin0/$end1",
                "$begin1/$end0",
                "$begin1/$end1"
            )
        } else {
            listOf(
                begin0 + '/' + picture.lowResPath,
                begin1 + '/' + picture.lowResPath
            )
        }

     val len = urlsToTry.size

    var currentInd by remember { mutableIntStateOf(0) }
    AsyncImage(
        model = ImageRequest.Builder(context)
            .data("http://" + urlsToTry[currentInd])
            .placeholder(R.drawable.ic_launcher_foreground)
            .listener(
                onError = { _, result ->

                    if (currentInd < len && result.throwable is HttpException) {
                        val exception = result.throwable as HttpException

                        if (exception.response.code == 404) {
                            currentInd++
                            println("got 404, going up for picture ${picture.name}")
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
