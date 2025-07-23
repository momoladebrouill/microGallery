package studio.lunabee.amicrogallery.lastmonth

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import studio.lunabee.amicrogallery.core.ui.R as CoreUi

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun LastMonthScreen(
    uiState: LastMonthUiState,
) {
    val bottomBarViewModel = koinInject<BottomBarViewModel>()
    LaunchedEffect(Unit) { bottomBarViewModel.showBottomBar(true) }
    if (uiState.pictures.isEmpty()) {
        EmptyList()
    } else {
        HasElements(uiState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmptyList() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(alignment = Alignment.Center)) {
            Icon(
                painter = painterResource(CoreUi.drawable.nopicture),
                contentDescription = stringResource(R.string.nothing_this_month),
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .align(Alignment.CenterHorizontally),
            )
            Text(text = stringResource(R.string.nothing_this_month), style = typography.title)
        }
    }
}

@OptIn(ExperimentalHazeMaterialsApi::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun HasElements(uiState: LastMonthUiState) {
    val listState = rememberLazyListState()
    val hazeState = remember { HazeState() }
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = listState,
    ) {
        stickyHeader {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
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
                        text =
                        stringResource(R.string.lastmonth_title),
                        color = colors.onMain,
                        modifier = Modifier
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
        items(uiState.pictures) { picture ->
            MicroGalleryButtonImage(picture, hazeState, showMe = { uiState.showPhoto(it) })
            Spacer(modifier = Modifier.padding(PaddingValues(spacing.SpacingMedium)))
        }
    }
}
