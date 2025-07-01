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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.LocalHazeStyle
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import studio.lunabee.amicrogallery.android.core.ui.component.photo.MicroGalleryButtonImage
import studio.lunabee.amicrogallery.android.core.ui.theme.CoreColorPalette
import studio.lunabee.amicrogallery.android.core.ui.theme.CoreColorTheme
import studio.lunabee.amicrogallery.android.core.ui.theme.CoreSpacing
import studio.lunabee.amicrogallery.android.core.ui.theme.CoreTypography
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme
import studio.lunabee.amicrogallery.app.R
import studio.lunabee.amicrogallery.utils.getMonthName
import studio.lunabee.microgallery.android.data.Picture

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun MonthLabel(year: String, month: String, key: String, hazeState: HazeState) {

    val statusBarPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + CoreSpacing.SpacingMedium

    Box() {
        Text(
            text =
                stringResource(
                    R.string.calendar_title,
                    getMonthName(month, stringArrayResource(R.array.months)),
                    year,
                ),
            color = MicroGalleryTheme.colors.onBackground,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = statusBarPadding,
                    start = CoreSpacing.SpacingMedium,
                    bottom = CoreSpacing.SpacingSmall,
                ),
            style = CoreTypography.header,
        )
    }
}


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun Pic(picture: Picture, hazeState: HazeState, fireAction: (CalendarAction) -> Unit) {
    MicroGalleryButtonImage(
        picture,
        modifier = Modifier.padding(CoreSpacing.SpacingSmall),
        hazeState = hazeState,
        showMe = { pictureId -> fireAction(CalendarAction.ShowPhoto(pictureId)) })
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalHazeMaterialsApi::class)
@Composable
fun ScrollTroughYear(calendarUiState: CalendarUiState, fireAction: (CalendarAction) -> Unit) {
    val hazeState = remember { HazeState() }
    val year = calendarUiState.yearSelected!!
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier
            .fillMaxHeight()
            .background(Color.Transparent)
            .hazeEffect(hazeState, HazeMaterials.thin(MicroGalleryTheme.colors.background)),
    ) {

        for (month in calendarUiState.monthsOfYears[year] ?: listOf()) {
            val key = "month:$month"
            item(key = key) {
                MonthLabel(
                    year = year,
                    month = month,
                    key = key,
                    hazeState = hazeState,
                )
            }
            item {
                HorizontalDivider(
                    color = MicroGalleryTheme.colors.second.copy(alpha = 0.5f),
                    modifier = Modifier
                        .padding(MicroGalleryTheme.spacing.SpacingMedium)
                        .clip(RoundedCornerShape(MicroGalleryTheme.radius.RadiusMedium))
                    ,
                    thickness = MicroGalleryTheme.spacing.SpacingSmall)
            }
            fireAction(CalendarAction.AskForExpand(month, year))
            items(calendarUiState.photosOfMonth[Pair(year, month)] ?: listOf()) {
                Pic(
                    picture = it,
                    hazeState = hazeState,
                    fireAction = fireAction
                )
            }
        }

    }
}