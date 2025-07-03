package studio.lunabee.amicrogallery.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import studio.lunabee.amicrogallery.android.core.ui.component.photo.MicroGalleryButtonImage
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.colors
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.radius
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.spacing
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.typography
import studio.lunabee.amicrogallery.app.R
import studio.lunabee.amicrogallery.utils.getMonthName
import studio.lunabee.microgallery.android.data.MMonth
import studio.lunabee.microgallery.android.data.MYear

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun MonthLabel(year: MYear, month: MMonth) {
    val statusBarPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    Box {
        Text(
            text =
            stringResource(
                R.string.calendar_title,
                getMonthName(month, stringArrayResource(R.array.months)),
                year,
            ),
            color = colors.onBackground,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = statusBarPadding + spacing.SpacingMedium,
                    start = spacing.SpacingMedium,
                    bottom = spacing.SpacingSmall,
                ),
            style = typography.header,
        )
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalHazeMaterialsApi::class)
@Composable
fun ScrollTroughYear(calendarUiState: CalendarUiState) {
    val hazeState = remember { HazeState() }
    val year = calendarUiState.yearSelected
    if (year == null) {
        calendarUiState.resetToHome()
    } else {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier
                .fillMaxHeight()
                .background(Color.Transparent)
                .hazeEffect(hazeState, HazeMaterials.thin(colors.background)),
        ) {
            for (month in calendarUiState.monthsOfYears[year] ?: listOf()) {
                item(key = "month:$month") {
                    MonthLabel(
                        year = year,
                        month = month,
                    )
                }
                item {
                    HorizontalDivider(
                        color = colors.second.copy(alpha = 0.5f),
                        modifier = Modifier
                            .padding(spacing.SpacingMedium)
                            .clip(RoundedCornerShape(radius.RadiusMedium)),
                        thickness = spacing.SpacingSmall,
                    )
                }
                calendarUiState.askForExpand(year, month)
                items(calendarUiState.photosOfMonth[Pair(year, month)] ?: listOf()) {
                    MicroGalleryButtonImage(
                        picture = it,
                        modifier = Modifier.padding(spacing.SpacingSmall),
                        hazeState = hazeState,
                        showMe = calendarUiState.showPhoto,
                    )
                }
            }
        }
    }
}
