package studio.lunabee.amicrogallery.calendar

import android.content.Context
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocal
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsCompat
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import studio.lunabee.amicrogallery.android.core.ui.component.image.MicroGalleryImage
import studio.lunabee.amicrogallery.android.core.ui.theme.CoreRadius
import studio.lunabee.amicrogallery.android.core.ui.theme.CoreSpacing
import studio.lunabee.amicrogallery.android.core.ui.theme.CoreTypography
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme
import studio.lunabee.amicrogallery.app.R
import studio.lunabee.amicrogallery.dashboard.LocalBottomBarHeight
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
        contentPadding = PaddingValues(
            top = (WindowInsets.statusBars.getTop(LocalDensity.current)/2).dp,
            bottom = LocalBottomBarHeight.current.dp
        )
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
            YearButton(it,
                navigateToYear = { onAction(CalendarAction.JumpToYear(it.year)) },
                showPictureInButton = {onAction(CalendarAction.ShowPhoto(it.picturePreview.id))}
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
                maxHeight = size
            )
        )
        layout(size, size) { placeable.place(0, 0) }
    }
)

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun YearButton(yearPreview: YearPreview, navigateToYear: () -> Unit, showPictureInButton : () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxHeight(0.3f)
            .pointerInput(Unit) {
            detectTapGestures(
                onLongPress = { _ -> println("yaa") ; showPictureInButton()},
                onTap = { _ -> navigateToYear()}
            )
        }.clip(RoundedCornerShape(CoreRadius.RadiusLarge)),

    ) {
        val hazeState = HazeState()
        Column(modifier = Modifier.padding(PaddingValues(horizontal = CoreSpacing.SpacingSmall, vertical = CoreSpacing.SpacingMedium))) {
            Box() {
                MicroGalleryImage(
                    picture = yearPreview.picturePreview,
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
