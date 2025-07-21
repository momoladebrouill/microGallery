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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.DragAndDropTransferData
import androidx.compose.ui.draganddrop.mimeTypes
import androidx.compose.ui.draganddrop.toAndroidDragEvent
import androidx.compose.ui.input.pointer.pointerInput
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import studio.lunabee.amicrogallery.android.core.ui.component.image.MicroGalleryImage
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.spacing
import studio.lunabee.amicrogallery.reorder.ReorderUiState
import studio.lunabee.microgallery.android.data.MicroPicture

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun ReorderGamingScreen(uiState: ReorderUiState.ReorderGamingUiState) {
    Box {
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
        ) {
            items(uiState.picturesInSlots.valuesOrdered().toList()) {
                DropBox(
                    uiState,
                    it,
                    modifier = Modifier.padding(
                        spacing.SpacingMedium,
                    ),
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
            Photo(it, uiState = uiState)
        }
    }
}

// the box in which we're dropping the elements
@RequiresApi(Build.VERSION_CODES.N)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DropBox(uiState: ReorderUiState.ReorderGamingUiState, index: Float, modifier: Modifier = Modifier) {
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
            Photo(picture, uiState = uiState)
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
fun Photo(picture: MicroPicture, modifier: Modifier = Modifier, uiState: ReorderUiState.ReorderGamingUiState) {
    MicroGalleryImage(
        modifier = modifier
            .pointerInput("aa") {
                detectTapGestures(
                    onDoubleTap = {
                        println("offset $it")
                        uiState.jumpToPicture(picture.id)
                    },
                )
            }
            .dragAndDropSource { _ ->
                DragAndDropTransferData(
                    ClipData.newPlainText(
                        "image id",
                        picture.id.toString(),
                    ),
                    flags = View.DRAG_FLAG_GLOBAL,
                )
            },
        picture = picture,
    )
}
