package studio.lunabee.amicrogallery.calendar

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import studio.lunabee.amicrogallery.android.core.ui.component.image.MicroGalleryImage
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.colors
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.radius
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.spacing
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.typography
import studio.lunabee.amicrogallery.app.R
import studio.lunabee.amicrogallery.dashboard.LocalBottomBarHeight
import studio.lunabee.microgallery.android.data.YearPreview
import studio.lunabee.amicrogallery.core.ui.R as CoreUi

@OptIn(ExperimentalHazeMaterialsApi::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CalendarScreen(
    calendarUiState: CalendarUiState,
    fireAction: (CalendarAction) -> Unit,
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        BackHandler(enabled = calendarUiState.yearSelected != null) {
            fireAction(CalendarAction.ResetToHome())
        }
        if (calendarUiState.yearSelected != null) {
            ScrollTroughYear(calendarUiState, fireAction)
        } else {
            Years(calendarUiState.years, fireAction)
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
        }
    }
}

@Composable
fun Years(years: List<YearPreview>, onAction: (CalendarAction) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(
            top = (WindowInsets.statusBars.getTop(LocalDensity.current) / 2).dp,
            bottom = LocalBottomBarHeight.current.dp,
        ),
    ) {
        stickyHeader {
            Column(modifier = Modifier.padding(start = spacing.SpacingMedium)) {
                Text(
                    text = "Calendar",
                    style = typography.header,
                )
                Text(
                    text = stringResource(R.string.select_year),
                    style = typography.body,
                )
            }
        }
        items(years) {
            YearButton(
                it,
                navigateToYear = { onAction(CalendarAction.JumpToYear(it.year)) },
                showPictureInButton = { onAction(CalendarAction.ShowPhoto(it.picturePreview.id)) },
            )
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
                maxHeight = size,
            ),
        )
        layout(size, size) { placeable.place(0, 0) }
    },
)

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun YearButton(yearPreview: YearPreview, navigateToYear: () -> Unit, showPictureInButton: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxHeight(0.3f)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { _ -> showPictureInButton() },
                    onTap = { _ -> navigateToYear() },
                )
            }.clip(RoundedCornerShape(radius.RadiusLarge)),

    ) {
        val hazeState = HazeState()
        Column(modifier = Modifier.padding(PaddingValues(horizontal = spacing.SpacingSmall, vertical = spacing.SpacingMedium))) {
            Box {
                MicroGalleryImage(
                    picture = yearPreview.picturePreview,
                    modifier = Modifier
                        .square()
                        .clip(RoundedCornerShape(radius.RadiusLarge))
                        .background(
                            Brush.verticalGradient(
                                Pair(0.0f, colors.main),
                                Pair(1.0f, colors.second),
                            ),
                        )
                        .hazeSource(state = hazeState),
                    contentScale = ContentScale.Crop,
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = spacing.SpacingSmall)
                        .clip(RoundedCornerShape(radius.RadiusSmall))
                        .fillMaxWidth(0.7f),
                ) {
                    Text(
                        text = yearPreview.qty.toString(),
                        style = typography.labelBold,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        modifier = Modifier
                            .background(Color.Transparent)
                            .align(Alignment.Center)
                            .hazeEffect(
                                state = hazeState,
                                style = HazeMaterials.ultraThin(
                                    Color.White,
                                ),
                            )
                            .fillMaxWidth(),

                    )
                }
            }
            Text(
                text = yearPreview.year,
                style = typography.action,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )
        }
    }
}
