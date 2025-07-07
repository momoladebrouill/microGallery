package studio.lunabee.amicrogallery.photoviewer

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import studio.lunabee.amicrogallery.android.core.ui.component.image.MicroGalleryImage
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.colors
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.spacing
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.typography
import studio.lunabee.amicrogallery.app.R
import studio.lunabee.amicrogallery.utils.getMonthName
import studio.lunabee.amicrogallery.core.ui.R as CoreUi

@Composable
fun PhotoViewerScreen(
    uiState: PhotoViewerUiState,
) {
    when (uiState) {
        is PhotoViewerUiState.Waiting -> PhotoWaitScreen()
        is PhotoViewerUiState.HasPicture -> PhotoView(uiState)
    }
}

@Composable
fun PhotoWaitScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = stringResource(R.string.waitingForData),
            style = typography.title,
            modifier = Modifier.align(Alignment.Center),
        )
    }
}

@Composable
fun PhotoView(uiState: PhotoViewerUiState.HasPicture) {
    var scale by remember { mutableFloatStateOf(1f) }
    var rotation by remember { mutableFloatStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
        scale *= zoomChange
        // add this if you want to allow the user to rotate the picture
        // rotation += rotationChange
        offset += offsetChange * 5.0f
    }
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        uiState.stopLoading()
    }

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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .statusBarsPadding(),
        ) {
            Text(
                text = uiState.picture.name.substringBefore("."),
                style = typography.body,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.Center),
            )
            IconButton(
                onClick = { uiState.share(context, launcher) },
                modifier = Modifier
                    .align(Alignment.CenterEnd),
            ) {
                if (uiState.loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(spacing.SpacingMedium),
                        color = colors.main,
                        trackColor = colors.second,
                    )
                } else {
                    Icon(
                        painter = painterResource(id = CoreUi.drawable.share),
                        contentDescription = stringResource(id = R.string.share),
                        tint = Color.White,
                    )
                }
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
            MicroGalleryImage(
                picture = uiState.picture,
                defaultToHighRes = true,
                modifier = Modifier
                    .align(Alignment.Center)
                    .graphicsLayer(rotationZ = rotation),
            )
        }

        Text(
            text = stringResource(
                R.string.month_year,
                getMonthName(uiState.picture.month ?: "", stringArrayResource(R.array.months)),
                uiState.picture.year.toString(),
            ),
            style = typography.title,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding(),
        )
    }
}
