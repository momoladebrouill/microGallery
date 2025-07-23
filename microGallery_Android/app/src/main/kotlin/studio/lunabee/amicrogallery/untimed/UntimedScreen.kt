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
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import org.koin.compose.koinInject
import studio.lunabee.amicrogallery.android.core.ui.component.photo.MicroGalleryButtonImage
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.colors
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.spacing
import studio.lunabee.amicrogallery.android.core.ui.theme.MicroGalleryTheme.typography
import studio.lunabee.amicrogallery.app.R
import studio.lunabee.amicrogallery.bottomBar.BottomBarViewModel

@Composable
fun UntimedScreen(
    uiState: UntimedUiState,
) {
    val bottomBarViewModel = koinInject<BottomBarViewModel>()
    LaunchedEffect(Unit) { bottomBarViewModel.showBottomBar(false) }
    val hazeState = remember { HazeState() }
    var topBarPadding by remember { mutableStateOf(0.dp) }
    Box {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = topBarPadding),
        ) {
            items(uiState.images) { picture ->
                MicroGalleryButtonImage(
                    picture = picture,
                    modifier = Modifier.padding(spacing.SpacingSmall),
                    hazeState = hazeState,
                    showMe = { uiState.showPhoto(it) },
                )
            }
        }
        UntimedHeader(
            hazeState = hazeState,
            modifier = Modifier.onGloballyPositioned { coordinates ->
                topBarPadding = (coordinates.size.height / 2).dp
            },
        )
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalHazeMaterialsApi::class)
@Composable
fun UntimedHeader(
    hazeState: HazeState,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(colors.main)
            .hazeEffect(
                state = hazeState,
                style = HazeMaterials.ultraThin(
                    colors.main,
                ),
            ),
    ) {
        Column {
            Spacer(modifier = Modifier.height(spacing.SpacingMedium))
            Text(
                text = stringResource(R.string.untimed_title),
                color = colors.onMain,
                modifier = modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(
                        start = spacing.SpacingMedium,
                        bottom = spacing.SpacingSmall,
                    ),
                style = typography.title,
            )
        }
    }
}
