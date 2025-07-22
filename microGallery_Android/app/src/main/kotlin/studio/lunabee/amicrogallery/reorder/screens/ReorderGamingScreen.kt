package studio.lunabee.amicrogallery.reorder.screens

import android.content.ClipData
import android.content.ClipDescription
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.draganddrop.dragAndDropSource
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.DragAndDropTransferData
import androidx.compose.ui.draganddrop.mimeTypes
import androidx.compose.ui.draganddrop.toAndroidDragEvent
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import studio.lunabee.amicrogallery.android.core.ui.component.image.MicroGalleryImage
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.colors
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.spacing
import studio.lunabee.amicrogallery.reorder.ReorderUiState
import studio.lunabee.amicrogallery.utils.clampScale
import studio.lunabee.microgallery.android.data.MicroPicture

@OptIn(ExperimentalHazeMaterialsApi::class)
@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun ReorderGamingScreen(uiState: ReorderUiState.ReorderGamingUiState) {
    val hazeState = HazeState()
    val listState: LazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .animateContentSize()
                .fillMaxSize()
                .dragAndDropTarget(
                    shouldStartDragAndDrop = { event ->
                        event.mimeTypes().contains(ClipDescription.MIMETYPE_TEXT_PLAIN)
                    },
                    target = object : DragAndDropTarget {
                        override fun onDrop(event: DragAndDropEvent): Boolean {
                            val clipData = event.toAndroidDragEvent().clipData
                            val picture: MicroPicture? = uiState.pictureMap[clipData.getItemAt(0).text.toString().toLong()]
                            if (picture != null) {
                                uiState.putPicture(0.0f, picture)
                            }
                            return true
                        }
                    },
                )
            ,

            state = listState
        ) {
            items(uiState.picturesInSlots.valuesOrdered().toList()) {
                DropBox(
                    uiState = uiState,
                    index = it,
                    modifier = Modifier.padding(
                        spacing.SpacingMedium,
                    ),
                    hazeState = hazeState,
                    scrollCallback = { offset ->
                        with(Dispatchers.IO){
                            coroutineScope.launch {
                                listState.scrollBy(-offset.y)
                            }
                        }
                    },
                )
            }
        }
        BottomAppBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .hazeEffect(
                    state = hazeState,
                    style = HazeMaterials.ultraThin(MaterialTheme.colorScheme.surfaceContainer)
                )
            ,
            containerColor = Color.Transparent
        ) {
            DragBox(uiState, modifier = Modifier.navigationBarsPadding())
        }
    }
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun DragBox(uiState: ReorderUiState.ReorderGamingUiState, modifier: Modifier) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        state = listState
    ) {
        items(uiState.picturesNotPlaced.toList()) {
            Spacer(Modifier.size(spacing.SpacingMedium))
            Photo(it, hazeState = null, scrollDown = { offset ->
                coroutineScope.launch {
                    listState.scrollBy(-offset.x)
                }
            })
        }
    }
}

// the box in which we're dropping the elements
@RequiresApi(Build.VERSION_CODES.N)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DropBox(
    uiState: ReorderUiState.ReorderGamingUiState,
    hazeState : HazeState,
    index: Float,
    modifier: Modifier = Modifier, scrollCallback : (Offset)->Unit) {
    val dropModifier: Modifier = modifier
        .dragAndDropTarget(
            shouldStartDragAndDrop = { event ->
                event.mimeTypes().contains(ClipDescription.MIMETYPE_TEXT_PLAIN)
            },
            target = object : DragAndDropTarget {
                override fun onDrop(event: DragAndDropEvent): Boolean {
                    val clipData = event.toAndroidDragEvent().clipData
                    val picture: MicroPicture? = uiState.pictureMap[clipData.getItemAt(0).text.toString().toLong()]
                    if (picture != null) {
                        uiState.putPicture(index, picture)
                    }
                    return true
                }

                override fun onExited(event: DragAndDropEvent) {
                }
            },
        )

    val picture: MicroPicture? = uiState.picturesInSlots[index]

    if (picture != null) {
        Box(modifier = dropModifier.background(colors.main)) {
            Text(picture.name)
            Photo(
                picture = picture,
                scrollDown = scrollCallback,
                hazeState = hazeState
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.N)
@OptIn(ExperimentalGlideComposeApi::class, ExperimentalFoundationApi::class)
@Composable
fun Photo(
    picture: MicroPicture,
    modifier: Modifier = Modifier,
    hazeState : HazeState?,
    scrollDown: (Offset) -> Unit) {
    var scale by remember { mutableFloatStateOf(1F) }
    var offset by remember {mutableStateOf(Offset.Zero)}
    var factor by remember { mutableFloatStateOf(1F/7F) } // damping factor
    scale -= (scale - 1.0F) * factor
    offset -= offset * factor
    factor -= (factor - 1f/7f) / 14f

    val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
        scale = clampScale(scale * zoomChange)
        if(scale >= 1.1f)
            offset += offsetChange
        else
            scrollDown(offsetChange)
        factor = 0f
    }
    Box(
        modifier = modifier
            .dragAndDropSource { _ ->
                DragAndDropTransferData(
                    ClipData.newPlainText(
                        "image id",
                        picture.id.toString(),
                    ),
                )
            }
    ) {
        MicroGalleryImage(
            modifier = Modifier

                .transformable(state = state)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    translationX = offset.x
                    translationY = offset.y
                }
                .then(
                    if(hazeState != null){
                        Modifier.hazeSource(hazeState)
                    }else Modifier
                ),
            picture = picture,

        )
    }
}
