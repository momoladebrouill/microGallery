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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import studio.lunabee.amicrogallery.android.core.ui.component.image.MicroGalleryImage
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.colors
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.spacing
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.typography
import studio.lunabee.amicrogallery.app.R
import studio.lunabee.amicrogallery.utils.clampOffset
import studio.lunabee.amicrogallery.utils.clampScale
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
    var shownScale by remember { mutableFloatStateOf(1f) }
    var shownRotation by remember { mutableFloatStateOf(0f) }
    var shownOffset by remember { mutableStateOf(Offset.Zero) }

    var currentOffset by remember { mutableStateOf(Offset.Zero) }

    var scale by remember { mutableFloatStateOf(1F) }
    var rotation by remember { mutableFloatStateOf(0f) }
    var pictureWidth by remember { mutableFloatStateOf(0f) }
    var pictureHeight by remember { mutableFloatStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
        scale = clampScale(scale * zoomChange)
        currentOffset = offsetChange
    }

    currentOffset -= currentOffset / 7f
    offset = clampOffset(offset + currentOffset, scale, pictureWidth, pictureHeight)
    shownScale += (scale - shownScale) / 7f
    shownRotation += (rotation - shownRotation) / 7f
    shownOffset += (offset - shownOffset) / 7f

    Box(
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        scale = 1f
                        rotation = 0f
                        currentOffset = -offset
                    },
                    onLongPress = {
                        rotation = rotation + 90.0f
                    },
                )
            }
            .fillMaxSize()
            .transformable(state = state)
            .background(Color.Black),

    ) {
        val pagerState = uiState.pagerState

        LaunchedEffect(pagerState.currentPageOffsetFraction == 0.0f) {
            if (pagerState.currentPage == 0) {
                uiState.switchTo(uiState.neighbors.first.id)
            } else if (pagerState.currentPage == 2) {
                uiState.switchTo(uiState.neighbors.second.id)
            }
        }
        HorizontalPager(
            userScrollEnabled = scale == 1.0f,
            pageSpacing = spacing.SpacingMedium,
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
        ) { index ->
            if (index == 1) {
                MicroGalleryImage(
                    picture = uiState.picture,
                    defaultToHighRes = (scale > 5.0f),
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxSize()
                        .onGloballyPositioned { coordinates ->
                            pictureWidth = coordinates.size.width.toFloat()
                            pictureHeight = coordinates.size.height.toFloat()
                        }
                        .graphicsLayer {
                            scaleX = shownScale
                            scaleY = shownScale
                            rotationZ = shownRotation
                            translationX = shownOffset.x
                            translationY = shownOffset.y
                        },
                )
            } else {
                MicroGalleryImage(
                    picture = if (index == 0) uiState.neighbors.first else uiState.neighbors.second,
                    modifier = Modifier.fillMaxSize(),
                    defaultToHighRes = false,
                )
            }
        }

        TopInformation(uiState, Modifier.align(Alignment.TopCenter))
        BottomInformation(uiState, Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
fun TopInformation(uiState: PhotoViewerUiState.HasPicture, modifier: Modifier) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result -> uiState.stopLoading() }
    Box(
        modifier = modifier
            .fillMaxWidth()
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
}

@Composable
fun BottomInformation(uiState: PhotoViewerUiState.HasPicture, modifier: Modifier) {
    Text(
        text = stringResource(
            R.string.month_year,
            getMonthName(uiState.picture.month, stringArrayResource(R.array.months)),
            uiState.picture.year.toString(),
        ),
        style = typography.title,
        color = Color.White,
        modifier = modifier
            .navigationBarsPadding(),
    )
}
