package studio.lunabee.amicrogallery.photoviewer

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
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
import androidx.compose.ui.input.pointer.PointerButtons
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.core.content.ContextCompat.startActivity
import studio.lunabee.amicrogallery.android.core.ui.component.image.MicroGalleryImage
import studio.lunabee.amicrogallery.app.R
import studio.lunabee.amicrogallery.calendar.getMonthName
import studio.lunabee.amicrogallery.core.ui.R as CoreUi
import java.nio.file.WatchEvent
import androidx.core.net.toUri

@Composable
fun PhotoViewerScreen(
    uiState: PhotoViewerUiState,
) {
    var scale by remember { mutableStateOf(1f) }
    var rotation by remember { mutableStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
        scale *= zoomChange
        //rotation += rotationChange
        offset += offsetChange * 5.0f
    }
    val context = LocalContext.current
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_STREAM, "http://92.150.239.130" + uiState.picture?.lowResPath)
        //putExtra(Intent.EXTRA_TITLE, uiState.picture?.name.toString().substringBefore("."))
        type = "image/jpeg"
       // setDataAndType(("http://92.150.239.130" + uiState.picture?.lowResPath).toUri(), "image/jpeg")
    }

    val shareIntent = Intent.createChooser(sendIntent, null)

    Box(modifier = Modifier
        .pointerInput(Unit) {
            detectTapGestures(
                onLongPress = {
                    scale = 1f
                    rotation = 0f
                    offset = Offset.Zero
                },
                onDoubleTap = {
                    rotation = rotation + 45.0f
                }
            )
        }
        .fillMaxSize()
        .background(Color.Black)) {
        Box(modifier = Modifier.fillMaxWidth().align(Alignment.TopCenter).statusBarsPadding()) {
            Text(
                text = uiState.picture?.name.toString().substringBefore("."),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.Center)
            )
            IconButton(
                onClick = { context.startActivity(shareIntent) },
                modifier = Modifier
                    .align(Alignment.CenterEnd)
            ) {
                Icon(
                    painter = painterResource(id = CoreUi.drawable.share),
                    contentDescription = stringResource(id = R.string.share),
                    tint = Color.White

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
                    translationY = offset.y
                )
                .transformable(state = state)

        ) {

            MicroGalleryImage(
                url = "http://92.150.239.130" + uiState.picture?.lowResPath,
                modifier = Modifier
                    .align(Alignment.Center)
                    .graphicsLayer(rotationZ = rotation),
            )

        }

        Text(
            text = getMonthName(uiState.picture?.month ?: "") + " " + uiState.picture?.year,
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
        )
    }
}