package studio.lunabee.amicrogallery.photoviewer

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.core.net.toUri
import studio.lunabee.amicrogallery.android.core.ui.component.image.MicroGalleryImage
import studio.lunabee.amicrogallery.app.R
import studio.lunabee.amicrogallery.utils.getMonthName

import studio.lunabee.amicrogallery.core.ui.R as CoreUi

@Composable
fun PhotoViewerScreen(
    uiState: PhotoViewerUiState,
) {
    var scale by remember { mutableFloatStateOf(1f) }
    var rotation by remember { mutableFloatStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
        scale *= zoomChange
        // rotation += rotationChange
        offset += offsetChange * 5.0f
    }
    val context = LocalContext.current
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        // TODO : download photo and send it to be shared
        putExtra(Intent.EXTRA_STREAM, "http://92.150.239.130" + uiState.picture?.lowResPath)
        // type = "image/jpeg"
        setDataAndType(("http://92.150.239.130" + uiState.picture?.lowResPath).toUri(), "image/jpeg")
    }

    val shareIntent = Intent.createChooser(sendIntent, null)

    Box(
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        scale = 1f
                        rotation = 0f
                        offset = Offset.Zero
                    },
                    onLongPress = {
                        rotation = rotation + 45.0f
                    },
                )
            }
            .fillMaxSize()
            .background(Color.Black),
    ) {
        Box(modifier = Modifier.fillMaxWidth().align(Alignment.TopCenter).statusBarsPadding()) {
            Text(
                text = uiState.picture?.name.toString().substringBefore("."),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.Center),
            )
            IconButton(
                onClick = { context.startActivity(shareIntent) },
                modifier = Modifier
                    .align(Alignment.CenterEnd),
            ) {
                Icon(
                    painter = painterResource(id = CoreUi.drawable.share),
                    contentDescription = stringResource(id = R.string.share),
                    tint = Color.White,

                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    rotationZ = rotation,
                    translationX = offset.x,
                    translationY = offset.y,
                )
                .transformable(state = state),

        ) {
            // TODO : why is it nullable ??
            if(uiState.picture != null )
            MicroGalleryImage(
                picture = uiState.picture,
                defaultToHighRes = true,
                modifier = Modifier
                    .align(Alignment.Center)
                    .graphicsLayer(rotationZ = rotation),
            )
        }

        Text(
            text = stringResource(R.string.month_year,
                getMonthName(uiState.picture?.month ?: "", stringArrayResource(R.array.months)), uiState.picture?.year.toString()),
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding(),
        )
    }
}
