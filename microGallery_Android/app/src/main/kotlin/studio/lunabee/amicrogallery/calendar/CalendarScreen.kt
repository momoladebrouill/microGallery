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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import studio.lunabee.amicrogallery.android.core.ui.component.image.MicroGalleryImage
import studio.lunabee.amicrogallery.android.core.ui.component.photo.MicroGalleryButtonImage
import studio.lunabee.amicrogallery.android.core.ui.theme.CoreRadius
import studio.lunabee.amicrogallery.android.core.ui.theme.CoreSpacing
import studio.lunabee.amicrogallery.android.core.ui.theme.CoreTypography
import studio.lunabee.amicrogallery.app.R
import studio.lunabee.microgallery.android.data.Picture
import studio.lunabee.amicrogallery.core.ui.R as CoreUi

@Composable
fun rememberNextMonth(state: LazyListState, key: String) = remember(state) {
    derivedStateOf {
        val items = state.layoutInfo.visibleItemsInfo.filter { it.key.toString().startsWith("month:") }
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
        header
    }
}

fun getMonthFromKey(key: String): String {
    return key.substringAfter(":")
}

@Composable
fun calculateInterpolationValue(currentShownYear: LazyListItemInfo?): Float {
    val density = LocalDensity.current.density
    val currentFirstHeightInDp = (currentShownYear?.size ?: 1) / density
    val currentFirstOffsetInDp = -(currentShownYear?.offset ?: 0) / density
    return currentFirstOffsetInDp / currentFirstHeightInDp
}

@OptIn(ExperimentalHazeMaterialsApi::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CalendarScreen(
    calendarUiState: CalendarUiState,
    fireAction: (CalendarAction) -> Unit
) {

    Box(modifier = Modifier.fillMaxWidth()) {
        if (calendarUiState.yearSelected != null)
            ScrollTroughYear(calendarUiState, fireAction)
        else
            ShowYears(calendarUiState.years, fireAction)

        IconButton(
            onClick = { fireAction(CalendarAction.JumpToSettings()) },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .statusBarsPadding(),
        ) {
            Icon(
                painter = painterResource(CoreUi.drawable.settings_24px),
                contentDescription = stringResource(R.string.settings),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ScrollTroughYear(calendarUiState: CalendarUiState, fireAction: (CalendarAction) -> Unit) {
    val lazyListState = rememberLazyListState()
    val hazeState = remember { HazeState() }
    val year = calendarUiState.yearSelected!!
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background),
        state = lazyListState,
    ) {

        for (month in calendarUiState.monthsOfYears[year] ?: listOf()) {
            val key = "month:$month"
            stickyHeader(key = key) {
                ShowMonthLabel(
                    lazyListState = lazyListState,
                    year = year,
                    month = month,
                    key = key,
                    hazeState = hazeState,
                )
            }
            fireAction(CalendarAction.AskForExpand(month, year))
            items(calendarUiState.photosOfMonth[Pair(year, month)] ?: listOf()){
                ShowPic(
                    picture = it,
                    hazeState = hazeState,
                    fireAction = fireAction
                )
            }
        }

    }
}

@Composable
fun ShowYears(years: List<Pair<String, String>>, onAction: (CalendarAction) -> Unit) {

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
        items(years) { (year, fullResExample) ->
            ShowYearButton(year, fullResExample, navigateToYear = { onAction(CalendarAction.JumpToYear(year)) })
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
fun ShowYearButton(yearName: String, fullResExample: String, navigateToYear: () -> Unit) {
    Button(
        onClick = navigateToYear,
        modifier = Modifier.fillMaxHeight(0.3f),
        colors = ButtonColors(containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = MaterialTheme.colorScheme.onBackground),
        shape = RoundedCornerShape(CoreRadius.RadiusLarge),
        contentPadding = PaddingValues(horizontal = CoreSpacing.SpacingSmall, vertical = CoreSpacing.SpacingMedium)

    ) {
        val hazeState = HazeState()
        Column {
            Box() {
                MicroGalleryImage(
                    url = "http://92.150.239.130$fullResExample",
                    modifier = Modifier
                        .square()
                        .clip(RoundedCornerShape(CoreRadius.RadiusLarge))
                        .hazeSource(state = hazeState),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = CoreSpacing.SpacingSmall)
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
                text = yearName,
                style = CoreTypography.action,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

        }
    }
}

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun ShowMonthLabel(lazyListState: LazyListState, year: String, month:String, key: String, hazeState: HazeState) {
    val isNext by rememberNextMonth(lazyListState, key = key) // is this header touching the top header ?
    val currentShownMonth by rememberActiveMonth(lazyListState)
    val isStuck = currentShownMonth?.key == key // is this the bar stuck at the top ?

    val statusBarPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + CoreSpacing.SpacingMedium

    val interpolationValue = calculateInterpolationValue(currentShownMonth)

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
            lerp(MaterialTheme.colorScheme.secondary, MaterialTheme.colorScheme.primary, interpolationValue)
        } else if (isStuck) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.secondary
        }

    Box(
        modifier = Modifier
            .background(animatedColor)
            .hazeEffect(
                state = hazeState,
                style = HazeMaterials.ultraThin(animatedColor),
            ),
    ) {
        if (isStuck) {
            Crossfade(targetState = currentShownMonth!!.key) { monthKey ->
                Text(
                    text =
                        stringResource(
                            R.string.calendar_title,
                            getMonthName(getMonthFromKey(monthKey.toString())), // month can be null
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
                    style = CoreTypography.header,
                )
            }
        } else {
            Text(
                text = getMonthName(month),
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = animatedPadding,
                        start = CoreSpacing.SpacingMedium,
                        bottom = CoreSpacing.SpacingSmall,
                    ),
                style = CoreTypography.header,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ShowPic(picture: Picture, hazeState: HazeState, fireAction: (CalendarAction) -> Unit) {
    MicroGalleryButtonImage(picture, hazeState = hazeState, showMe = { pictureId -> fireAction(CalendarAction.ShowPhoto(pictureId)) })
    Spacer(modifier = Modifier.padding(PaddingValues(CoreSpacing.SpacingMedium)))
}

@Composable
fun getMonthName(value: String): String {
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
