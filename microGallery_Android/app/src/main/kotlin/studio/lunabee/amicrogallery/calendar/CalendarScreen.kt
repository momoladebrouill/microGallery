package studio.lunabee.amicrogallery.calendar

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonShapes
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import studio.lunabee.amicrogallery.android.core.ui.component.image.MicroGalleryImage
import studio.lunabee.amicrogallery.android.core.ui.theme.CoreRadius
import studio.lunabee.amicrogallery.android.core.ui.theme.CoreSpacing
import studio.lunabee.amicrogallery.app.R
import studio.lunabee.microgallery.android.domain.Directory
import studio.lunabee.microgallery.android.domain.Node
import studio.lunabee.microgallery.android.domain.Picture

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalHazeMaterialsApi::class)
@Composable
fun CalendarScreen(calendarUiState: CalendarUiState, hazeState: HazeState?) {
    if (calendarUiState.rootNode == null) {
        LoadingScreen()
    } else {
        ShowPhotos(calendarUiState.rootNode, hazeState!!)
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
fun ShowPhotos(rootNode: Node, hazeState: HazeState) {
    val lazyListState = rememberLazyListState()
    LazyColumn(
        modifier = Modifier.fillMaxHeight().background(MaterialTheme.colorScheme.background),
        state = lazyListState,
    ) {
        val rootDir: Directory = rootNode as Directory

        for (yearNode in rootDir.content) {
            val key = "year:${yearNode.name}"
            stickyHeader(key = key) {
                val isNext by rememberNextYear(lazyListState, key = key) // es ce que c'est la bar qui touche la top bar
                val currentShownYear by rememberActiveYear(lazyListState)
                val currentShownMonthKey by rememberActiveMonth(lazyListState)
                val isStuck = currentShownYear?.key == key // is this the bar stuck at the top

                val statusBarPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + CoreSpacing.SpacingMedium

                val density = LocalDensity.current.density
                val currentFirstHeightInDp = (currentShownYear?.size ?: 1) / density
                val currentFirstOffsetInDp = -(currentShownYear?.offset ?:0) / density
                val interpolationValue = currentFirstOffsetInDp / currentFirstHeightInDp
                val animatedPadding =
                    if (isNext) {
                        CoreSpacing.SpacingMedium + (statusBarPadding - CoreSpacing.SpacingMedium) * interpolationValue
                    } else if (isStuck) {
                        statusBarPadding
                    } else {
                        CoreSpacing.SpacingMedium
                    }

                val animatedColor =
                    if (isNext) {
                        lerp(MaterialTheme.colorScheme.tertiary, MaterialTheme.colorScheme.primary, interpolationValue)
                    } else if (isStuck) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.tertiary
                    }

                Box(
                    modifier = Modifier
                        .background(Color.Transparent)
                        .hazeEffect(
                            state = hazeState,
                            style = HazeMaterials.ultraThin(
                                animatedColor,
                            ),
                        ),
                ) {
                    Crossfade(targetState = currentShownMonthKey) { monthKey ->
                        Text(
                            text = if (isStuck) {
                                stringResource(
                                    R.string.calendar_title,
                                    getLabelName(getMonthFromKey(monthKey.toString())),
                                    yearNode.name,
                                )
                            } else {
                                yearNode.name
                            },
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    top = animatedPadding,
                                    start = CoreSpacing.SpacingMedium,
                                    bottom = CoreSpacing.SpacingSmall,
                                ),
                            style = MaterialTheme.typography.titleLarge,
                        )
                    }
                }
            }
            val yearDir: Directory = yearNode as Directory
            items(
                items = yearDir.content,
                key = { monthNode -> "month:${yearNode.name}/${monthNode.name}" },
            ) { monthNode ->
                ShowNode(monthNode)
                val monthDir: Directory = monthNode as Directory
                for (picture in monthDir.content) {
                    ShowNode(picture, modifier = Modifier.hazeSource(state = hazeState))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ShowNode(node: Node, modifier: Modifier = Modifier) {
    when (node) {
        is Directory -> {
            Text(
                text = getLabelName(node.name),
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = CoreSpacing.SpacingMedium, top = CoreSpacing.SpacingMedium, bottom = CoreSpacing.SpacingSmall),
                style = MaterialTheme.typography.headlineMedium,
            )
        }
        is Picture -> {
            Button(
                onClick = { /* TODO : jump in order to preview image in full screen clicked */ },
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.padding(PaddingValues(CoreSpacing.SpacingMedium)),
                shapes = ButtonShapes(RoundedCornerShape(CoreRadius.RadiusMedium), RoundedCornerShape(CoreRadius.RadiusMedium)),

            ) {
                Box {
                    Text(text = stringResource(R.string.loading, node.name), modifier = Modifier.align(Alignment.Center))
                    MicroGalleryImage(
                        url = "http://92.150.239.130" + node.lowResPath,
                        // TODO : better MicroGalleryImage to call with only a Picture (fallback to highRes etc)
                        modifier = modifier.fillMaxWidth().wrapContentHeight(),
                    )
                }
            }
            Spacer(modifier = Modifier.padding(PaddingValues(CoreSpacing.SpacingMedium)))
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
