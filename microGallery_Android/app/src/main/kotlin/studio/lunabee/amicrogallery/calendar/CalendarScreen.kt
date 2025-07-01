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
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridItemInfo
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
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
import studio.lunabee.amicrogallery.android.core.ui.theme.CoreColorPalette
import studio.lunabee.amicrogallery.android.core.ui.theme.CoreColorTheme
import studio.lunabee.amicrogallery.android.core.ui.theme.CoreRadius
import studio.lunabee.amicrogallery.android.core.ui.theme.CoreSpacing
import studio.lunabee.amicrogallery.android.core.ui.theme.CoreTypography
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme
import studio.lunabee.amicrogallery.app.R
import studio.lunabee.microgallery.android.data.Picture
import studio.lunabee.microgallery.android.data.YearPreview
import studio.lunabee.amicrogallery.core.ui.R as CoreUi

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
@Composable
fun ShowYears(years: List<YearPreview>, onAction: (CalendarAction) -> Unit) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.statusBarsPadding()
    ) {
        stickyHeader {
            Column(modifier = Modifier.padding(start = CoreSpacing.SpacingMedium)) {
                Text(
                    text = "Calendar",
                    style = MicroGalleryTheme.typography.header,
                )
                Text(
                    text = stringResource(R.string.select_year),
                    style = MicroGalleryTheme.typography.body,
                )
            }
        }
        items(years) {
            ShowYearButton(it, navigateToYear = { onAction(CalendarAction.JumpToYear(it.year)) })
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
fun ShowYearButton(yearPreview: YearPreview, navigateToYear: () -> Unit) {
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
                    url = "http://92.150.239.130${yearPreview.linkPreview}",
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
                        text = yearPreview.qty.toString(),
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
                text = yearPreview.year,
                style = CoreTypography.action,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

        }
    }
}
