package studio.lunabee.amicrogallery.calendar

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import studio.lunabee.amicrogallery.android.core.ui.component.photo.MicroGalleryButtonImage
import studio.lunabee.amicrogallery.android.core.ui.theme.CoreRadius
import studio.lunabee.amicrogallery.android.core.ui.theme.CoreSpacing
import studio.lunabee.amicrogallery.android.core.ui.theme.CoreTypography
import studio.lunabee.amicrogallery.app.R
import studio.lunabee.amicrogallery.calendar.displayed.MonthDisplay
import studio.lunabee.amicrogallery.calendar.displayed.PhotoDisplay
import studio.lunabee.microgallery.android.data.Picture
import studio.lunabee.amicrogallery.core.ui.R as CoreUi

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
        val topBar = state.layoutInfo.visibleItemsInfo.filter { it.key.toString().startsWith("year:") }.getOrNull(0)
            ?: return@derivedStateOf false
        val items = state.layoutInfo.visibleItemsInfo.filter {
            (
                it.key.toString().startsWith("month:")
                    || it.key.toString().startsWith("picture:")
                ) && it.offset >= topBar.size / 2
        }
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
    fireAction: (CalendarAction) -> Unit,
) {
    val hazeState = remember { HazeState() }
    val lazyListState = rememberLazyListState()
    Box(modifier = Modifier.fillMaxWidth()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.background),
            state = lazyListState,
        ) {
            for (year in calendarUiState.years) {
                val key = "year:$year"
                stickyHeader(key = key) {
                    ShowYearLabel(
                        lazyListState = lazyListState,
                        year = year,
                        key = key,
                        hazeState = hazeState,
                    )
                }
                // the current month that we're scrolling through
                var currentMonth: String? = null
                items(
                    items = calendarUiState.getItemsToShow(year),
                    key = { display ->
                        when (display) {
                            is MonthDisplay -> {
                                currentMonth = display.name
                                "month:$year/${display.name}"
                            }
                            // place the month after the / to be obtained later
                            is PhotoDisplay -> "picture:${display.picture.name}/$currentMonth"
                        }
                    },
                ) { display -> // the display item
                    when (display) {
                        is MonthDisplay -> {
                            Button(
                                onClick = {
                                    if (Pair(year, display.name) in calendarUiState.expandedMonths) {
                                        // if this month is already expanded
                                        fireAction(CalendarAction.AskForCollapse(display.name, year))
                                    } else {
                                        fireAction(CalendarAction.AskForExpand(display.name, year))
                                    }
                                },
                                contentPadding = PaddingValues(0.dp),
                                modifier = Modifier.background(MaterialTheme.colorScheme.tertiary),
                                colors = ButtonColors(Color.Transparent, Color.Transparent, Color.Transparent, Color.Transparent),
                                shapes = ButtonShapes(
                                    RoundedCornerShape(CoreRadius.RadiusMedium),
                                    RoundedCornerShape(CoreRadius.RadiusMedium),
                                ),
                            ) {
                                ShowMonth(display.name)
                            }
                        }

                        is PhotoDisplay -> {
                            ShowPic(display.picture, hazeState = hazeState, fireAction = fireAction)
                        }
                    }
                }
            }
        }

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


@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun ShowYearLabel(lazyListState: LazyListState, year: String, key: String, hazeState: HazeState) {
    val isNext by rememberNextYear(lazyListState, key = key) // is this header touching the top header ?
    val currentShownYear by rememberActiveYear(lazyListState)
    val currentShownMonth by rememberActiveMonth(lazyListState)
    val isStuck = currentShownYear?.key == key // is this the bar stuck at the top ?

    val statusBarPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + CoreSpacing.SpacingMedium

    val interpolationValue = calculateInterpolationValue(currentShownYear)

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
                style = HazeMaterials.ultraThin(
                    animatedColor,
                ),
            ),
    ) {
        if (isStuck) {
            Crossfade(targetState = currentShownMonth) { month ->
                Text(
                    text =
                    stringResource(
                        R.string.calendar_title,
                        getMonthName(month.toString()), // month can be null
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
        } else {
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
}

@Composable
fun ShowMonth(month: String) { // we set here only the text, not the background
    Text(
        text = getMonthName(month),
        color = MaterialTheme.colorScheme.onTertiary,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = CoreSpacing.SpacingMedium, top = CoreSpacing.SpacingMedium, bottom = CoreSpacing.SpacingSmall),
        style = MaterialTheme.typography.headlineMedium,
    )
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
