package studio.lunabee.amicrogallery.reorder

import android.content.ClipData
import android.content.ClipDescription
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.draganddrop.dragAndDropSource
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.DragAndDropTransferData
import androidx.compose.ui.draganddrop.mimeTypes
import androidx.compose.ui.draganddrop.toAndroidDragEvent
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.colors
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.spacing

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun ReorderScreen(uiState: ReorderUiState) {
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
        stickyHeader {
            DragBox(uiState)
        }
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
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun DragBox(uiState: ReorderUiState) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colors.second)
            .statusBarsPadding(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        uiState.picturesNotPlaced.forEach { url ->
            Photo(url, modifier = Modifier.weight(1f))
        }
    }
}

// the box in which we're dropping the elements
@RequiresApi(Build.VERSION_CODES.N)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DropBox(uiState: ReorderUiState, index: Float, modifier: Modifier = Modifier) {
    val dropModifier: Modifier = modifier
        .dragAndDropTarget(
            shouldStartDragAndDrop = { event ->
                event.mimeTypes().contains(ClipDescription.MIMETYPE_TEXT_PLAIN)
            },
            target = object : DragAndDropTarget {
                override fun onDrop(event: DragAndDropEvent): Boolean {
                    val clipData = event.toAndroidDragEvent().clipData
                    uiState.putPicture(index, clipData.getItemAt(0).text.toString())
                    return true
                }
            },
        )

    val url: String? = uiState.picturesInSlots[index]

    if (url != null) {
        Box(modifier = dropModifier) {
            Text(url)
            Photo(url)
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
fun Photo(urlStr: String, modifier: Modifier = Modifier) {
    GlideImage(
        model = urlStr,
        contentDescription = "demo",
        modifier = modifier
            .dragAndDropSource { _ ->
                DragAndDropTransferData(
                    ClipData.newPlainText(
                        "image Url",
                        urlStr,
                    ),
                    flags = View.DRAG_FLAG_GLOBAL,
                )
            },
    )
}
