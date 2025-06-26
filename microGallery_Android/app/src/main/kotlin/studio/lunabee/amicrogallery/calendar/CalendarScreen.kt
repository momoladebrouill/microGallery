package studio.lunabee.amicrogallery.calendar

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonShapes
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import studio.lunabee.amicrogallery.calendar.displayed.Display
import studio.lunabee.amicrogallery.calendar.displayed.MonthDisplay
import studio.lunabee.amicrogallery.calendar.displayed.PhotoDisplay
import studio.lunabee.microgallery.android.data.Picture
import kotlin.collections.getOrNull
import kotlin.math.exp

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
        val topBar = state.layoutInfo.visibleItemsInfo.filter { it.key.toString().startsWith("year:") }.getOrNull(0) ?: return@derivedStateOf false
        val items = state.layoutInfo.visibleItemsInfo.filter {
            (it.key.toString().startsWith("month:")
                || it.key.toString().startsWith("picture:")) && it.offset >= topBar.size/2 }
        val header = items.getOrNull(0) ?: return@derivedStateOf null
        getMonthFromKey(header.key.toString())
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

@OptIn(ExperimentalHazeMaterialsApi::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CalendarScreen(calendarUiState: CalendarUiState,
    hazeState: HazeState,
    fireAction : (CalendarAction) -> Unit) {
    val lazyListState = rememberLazyListState()
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background),
        state = lazyListState,
    ) {
        for (year in calendarUiState.years) {
            val key = "year:$year"
            stickyHeader(key = key) {
                val isNext by rememberNextYear(lazyListState, key = key) // es ce que c'est la bar qui touche la top bar
                val currentShownYear by rememberActiveYear(lazyListState)
                val currentShownMonth by rememberActiveMonth(lazyListState)
                val isStuck = currentShownYear?.key == key // is this the bar stuck at the top

                val statusBarPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + CoreSpacing.SpacingMedium

                val density = LocalDensity.current.density
                val currentFirstHeightInDp = (currentShownYear?.size ?: 1) / density
                val currentFirstOffsetInDp = -(currentShownYear?.offset ?: 0) / density
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
                        .background(animatedColor)
                        .hazeEffect(
                            state = hazeState,
                            style = HazeMaterials.ultraThin(
                                animatedColor,
                            ),
                        ),
                ) {
                    if (isStuck)
                        Crossfade(targetState = currentShownMonth) { month->
                            Text(
                                text =
                                    stringResource(
                                        R.string.calendar_title,
                                        getLabelName(month.toString()), // month can be null
                                        year,
                                    ),
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
                    else
                        Text(
                            text = year,
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
            var currentMonth : String? = null
            items(
                items = calendarUiState.getItemsToShow(year),
                key = {
                    display -> when(display) {
                        is MonthDisplay -> {currentMonth = display.name ;"month:$year/${display.name}"}
                        is PhotoDisplay -> "picture:${display.picture.name}/${currentMonth}"
                }
                      },
            ) { display ->
                when (display) {
                    is MonthDisplay -> {
                        Button(
                            onClick = {

                                if(Pair(year, display.name) in calendarUiState.expandedMonths)
                                    fireAction(CalendarAction.AskForCollapse(display.name, year))
                                else
                                    fireAction(CalendarAction.AskForExpand(display.name, year))
                            },
                            contentPadding = PaddingValues(0.dp),
                            modifier = Modifier.background(MaterialTheme.colorScheme.surfaceDim),
                            colors = ButtonColors(Color.Transparent, Color.Transparent, Color.Transparent, Color.Transparent),
                            shapes = ButtonShapes(RoundedCornerShape(CoreRadius.RadiusMedium), RoundedCornerShape(CoreRadius.RadiusMedium)),

                            ) {
                            ShowMonth(display.name)
                        }
                    }

                    is PhotoDisplay -> {
                        ShowChild(display.picture, modifier = Modifier.hazeSource(state = hazeState))
                    }
                }

            }
        }
    }

}

@Composable
fun ShowMonth(month: String) {
    Text(
        text = getLabelName(month),
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = CoreSpacing.SpacingMedium, top = CoreSpacing.SpacingMedium, bottom = CoreSpacing.SpacingSmall),
        style = MaterialTheme.typography.headlineMedium,
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ShowChild(picture: Picture, modifier: Modifier = Modifier) {
    Button(
        onClick = { /* TODO : jump in order to preview image in full screen clicked */ },
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier.padding(PaddingValues(CoreSpacing.SpacingMedium)),
        shapes = ButtonShapes(RoundedCornerShape(CoreRadius.RadiusMedium), RoundedCornerShape(CoreRadius.RadiusMedium)),

        ) {
        Box {
            Text(text = stringResource(R.string.loading, picture.name), modifier = Modifier.align(Alignment.Center))
            MicroGalleryImage(
                url = "http://92.150.239.130" + picture.lowResPath,
                // TODO : better MicroGalleryImage to call with only a Picture (fallback to highRes etc)
                modifier = modifier
                    .wrapContentHeight(),
            )
        }
    }
    Spacer(modifier = Modifier.padding(PaddingValues(CoreSpacing.SpacingMedium)))
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
