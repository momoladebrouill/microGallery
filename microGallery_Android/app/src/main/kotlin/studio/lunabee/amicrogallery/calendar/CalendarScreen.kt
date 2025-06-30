package studio.lunabee.amicrogallery.calendar

import android.annotation.SuppressLint
import android.graphics.drawable.ShapeDrawable
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonShapes
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import studio.lunabee.amicrogallery.android.core.ui.component.image.MicroGalleryImage
import studio.lunabee.amicrogallery.android.core.ui.theme.CoreRadius
import studio.lunabee.amicrogallery.android.core.ui.theme.CoreSpacing
import studio.lunabee.amicrogallery.android.core.ui.theme.CoreTypography
import studio.lunabee.amicrogallery.app.R
import studio.lunabee.amicrogallery.core.ui.R as CoreUi
import studio.lunabee.microgallery.android.domain.Directory
import studio.lunabee.microgallery.android.domain.Node
import studio.lunabee.microgallery.android.domain.Picture

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalHazeMaterialsApi::class)
@Composable
fun CalendarScreen(calendarUiState: CalendarUiState, onAction: (CalendarAction) -> Unit) {
    val hazeState = remember { HazeState() }
    if (calendarUiState.rootNode == null) {
        LoadingScreen()
    } else {
        ShowPhotos(calendarUiState.rootNode, hazeState, onAction)
    }
}

@Composable
fun LoadingScreen() {
    Column {
        Text(
            text = stringResource(R.string.waitingForData),
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            text = stringResource(R.string.notLong),
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
fun rememberNextYear(state: LazyListState, key: String) = remember(state) {
    derivedStateOf {
        val items = state.layoutInfo.visibleItemsInfo.filter { it.key.toString().startsWith("year:") }
        val nextElement = items.getOrNull(1) ?: return@derivedStateOf false
        val header = items.getOrNull(0) ?: return@derivedStateOf false
        nextElement.key == key && header.size >= nextElement.offset
    }
}

@Composable
fun rememberActiveMonth(state: LazyListState) = remember(state) {
    derivedStateOf {
        val items = state.layoutInfo.visibleItemsInfo.filter { it.key.toString().startsWith("month:") }
        val header = items.getOrNull(0) ?: return@derivedStateOf null
        header.key
    }
}

@Composable
fun rememberActiveYear(state: LazyListState) = remember(state) {
    derivedStateOf {
        val items = state.layoutInfo.visibleItemsInfo.filter { it.key.toString().startsWith("year:") }
        val header = items.getOrNull(0) ?: return@derivedStateOf null
        header
    }
}

fun getMonthFromKey(key: String): String {
    return key.substringAfter("/")
}

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun ShowPhotos(rootNode: Node, hazeState: HazeState, onAction: (CalendarAction) -> Unit) {
    val rootDir = rootNode as Directory
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.statusBarsPadding()
    ) {
        stickyHeader {
            Column(modifier = Modifier.padding(start = CoreSpacing.SpacingMedium)) {
                Text(
                    text = "Calendar",
                    style = CoreTypography.header,
                )
                Text(
                    text = stringResource(R.string.select_year),
                    style = CoreTypography.body,
                )
            }
        }
        items(rootDir.content){
            if(it.name != "untimed")
            ShowYear(it as Directory, navigateToYear = {onAction(CalendarAction.JumpToYear(it.name))})
        }
    }

}
fun Modifier.square(): Modifier = this.then(
    this.layout { measurable, constraints ->
        val size = minOf(constraints.maxWidth, constraints.maxHeight)
        val placeable = measurable.measure(
            constraints.copy(
                minWidth = size,
                maxWidth = size,
                minHeight = size,
                maxHeight = size
            )
        )
        layout(size, size) { placeable.place(0, 0) }
    }
)

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun ShowYear(year : Directory, navigateToYear : () -> Unit){
    Button(
        onClick = navigateToYear,
        modifier = Modifier.fillMaxHeight(0.3f),
        colors = ButtonColors(containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = MaterialTheme.colorScheme.onBackground),
        shape = RoundedCornerShape(CoreRadius.RadiusLarge),
        contentPadding = PaddingValues(horizontal = CoreSpacing.SpacingSmall, vertical = CoreSpacing.SpacingMedium)

    ){
        val hazeState = HazeState()
        Column {
            Box() {
                MicroGalleryImage(
                    url = "http://92.150.239.130" + (((year.content[0] as Directory).content.shuffled()[0]) as Picture).fullResPath,
                    modifier = Modifier
                        .square()
                        .clip(RoundedCornerShape(CoreRadius.RadiusLarge))
                        .hazeSource(state = hazeState)
                    ,
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom =CoreSpacing.SpacingSmall)
                        .clip(RoundedCornerShape(CoreRadius.RadiusSmall))
                        .fillMaxWidth(0.7f)
                ) {
                    Text(
                        text = "1245",
                        style = CoreTypography.labelBold,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        modifier = Modifier
                            .background(Color.Red)
                            .align(Alignment.Center)
                            .hazeEffect(
                                state = hazeState,
                                style = HazeMaterials.ultraThin(
                                    Color.White,
                                )
                            )
                            .fillMaxWidth()

                    )
                }
            }
            Text(
                text = year.name,
                style = CoreTypography.action,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

        }
    }

}


@Composable
fun getLabelName(value: String): String {
    val task = runCatching {
        val number = value.toInt()
        if (number < 13) {
            stringArrayResource(R.array.french_months)[number - 1]
        } else {
            value
        }
    }
    return task.getOrElse { value }
}
