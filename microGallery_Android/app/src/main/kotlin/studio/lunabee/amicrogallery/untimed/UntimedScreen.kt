package studio.lunabee.amicrogallery.untimed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import studio.lunabee.amicrogallery.android.core.ui.component.photo.MicroGalleryButtonImage
import studio.lunabee.amicrogallery.android.core.ui.theme.CoreColorTheme
import studio.lunabee.amicrogallery.android.core.ui.theme.CoreSpacing
import studio.lunabee.amicrogallery.android.core.ui.theme.CoreTypography
import studio.lunabee.amicrogallery.android.core.ui.theme.LocalCoreColor
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme
import studio.lunabee.amicrogallery.app.R
import studio.lunabee.amicrogallery.calendar.CalendarUiState


@Composable
fun UntimedScreen(
    uiState: UntimedUiState,
    fireAction: (UntimedAction) -> Unit,
) {
    val hazeState = remember { HazeState() }
    Box() {

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
        ) {
            item(){
                Spacer(modifier = Modifier.padding(CoreSpacing.SpacingMedium))
            }
            items(uiState.images) { picture ->
                MicroGalleryButtonImage(picture, hazeState, { fireAction(UntimedAction.ShowPhoto(it)) })
                Spacer(modifier = Modifier.padding(PaddingValues(CoreSpacing.SpacingMedium)))
            }
        }
        UntimedHeader(hazeState = hazeState)
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalHazeMaterialsApi::class)
@Composable
fun UntimedHeader(
    hazeState: HazeState
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .hazeEffect(
                state = hazeState,
                style = HazeMaterials.ultraThin(
                    MaterialTheme.colorScheme.primary,
                ),
            ),
    ) {
        Column {
            Spacer(modifier = Modifier.height(CoreSpacing.SpacingMedium))
            Text(
                text =
                    stringResource(R.string.untimed_title),
                color = MicroGalleryTheme.colors.onMain,
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(
                        start = CoreSpacing.SpacingMedium,
                        bottom = CoreSpacing.SpacingSmall,
                    ),
                style = CoreTypography.title,
            )
        }
    }
}
