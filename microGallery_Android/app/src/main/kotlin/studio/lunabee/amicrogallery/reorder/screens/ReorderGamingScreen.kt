package studio.lunabee.amicrogallery.reorder.screens

import android.content.ClipData
import android.content.ClipDescription
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.draganddrop.dragAndDropSource
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.BottomAppBar
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.option.viewModelScopeFactory
import studio.lunabee.amicrogallery.android.core.ui.component.image.MicroGalleryImage
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.spacing
import studio.lunabee.amicrogallery.photoviewer.PhotoViewerAction
import studio.lunabee.amicrogallery.reorder.ReorderUiState
import studio.lunabee.amicrogallery.utils.clampScale
import studio.lunabee.microgallery.android.data.MicroPicture

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun ReorderGamingScreen(uiState: ReorderUiState.ReorderGamingUiState) {
    val listState: LazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .animateContentSize()
                .then(
                    if (uiState.picturesInSlots.contains(0.0f)) {
                        Modifier.fillMaxSize()
                    } else {
                        Modifier
                    },
                ),
            verticalArrangement = Arrangement.SpaceEvenly,
            state = listState
        ) {
            items(uiState.picturesInSlots.valuesOrdered().toList()) {
                DropBox(
                    uiState = uiState,
                    index = it,
                    modifier = Modifier.padding(
                        spacing.SpacingMedium,
                    ),
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
        BottomAppBar(modifier = Modifier.align(Alignment.BottomCenter)) {
            DragBox(uiState, modifier = Modifier.navigationBarsPadding())
        }
    }
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun DragBox(uiState: ReorderUiState.ReorderGamingUiState, modifier: Modifier) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items(uiState.picturesNotPlaced.toList()) {
            Spacer(Modifier.size(spacing.SpacingMedium))
            Photo(it, uiState = uiState, scrollDown = {})
        }
    }
}

// the box in which we're dropping the elements
@RequiresApi(Build.VERSION_CODES.N)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DropBox(uiState: ReorderUiState.ReorderGamingUiState, index: Float, modifier: Modifier = Modifier, scrollCallback : (Offset)->Unit) {
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
            },
        )

    val picture: MicroPicture? = uiState.picturesInSlots[index]

    if (picture != null) {
        Box(modifier = dropModifier) {
            Text(picture.name)
            Photo(
                picture = picture,
                uiState = uiState,
                scrollDown = scrollCallback
            )
        }
    } else {
        NoOrder(dropModifier)
    }
}

@Composable
fun NoOrder(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "Start by dropping a picture in the middle !!",
        )
    }
}

@RequiresApi(Build.VERSION_CODES.N)
@OptIn(ExperimentalGlideComposeApi::class, ExperimentalFoundationApi::class)
@Composable
fun Photo(
    picture: MicroPicture,
    modifier: Modifier = Modifier,
    uiState: ReorderUiState.ReorderGamingUiState,
    scrollDown: (Offset) -> Unit) {
    var scale by remember { mutableFloatStateOf(1F) }
    var factor by remember { mutableStateOf(1F) }
    scale -= (scale - 1.0F)/7 * factor

    factor = 1f

    val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
        scale = clampScale(scale * zoomChange)
        factor = 0f
        scrollDown(offsetChange)

    }

    MicroGalleryImage(
        modifier = modifier
            .dragAndDropSource { _ ->
                DragAndDropTransferData(
                    ClipData.newPlainText(
                        "image id",
                        picture.id.toString(),
                    ),
                    flags = View.DRAG_FLAG_GLOBAL,
                )
            }
            .transformable(state = state)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
        ,
        picture = picture,
    )
}
