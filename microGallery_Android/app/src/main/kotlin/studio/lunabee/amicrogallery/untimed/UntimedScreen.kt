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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonShapes
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import studio.lunabee.amicrogallery.android.core.ui.component.image.MicroGalleryImage
import studio.lunabee.amicrogallery.android.core.ui.theme.CoreRadius
import studio.lunabee.amicrogallery.android.core.ui.theme.CoreSpacing
import studio.lunabee.amicrogallery.app.R

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalHazeMaterialsApi::class)
@Composable
fun UntimedScreen(
    uiState: UntimedUiState,
) {
    val hazeState = remember { HazeState() }
    val listState = rememberLazyListState()

        LazyColumn(modifier = Modifier.fillMaxSize(),
            state = listState) {
            stickyHeader {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primary)
                        .hazeEffect(
                            state = hazeState,
                            style = HazeMaterials.ultraThin(
                                MaterialTheme.colorScheme.primary
                            ),
                        ),
                ) {
                    Column {
                        Spacer(modifier = Modifier.height(CoreSpacing.SpacingMedium))
                        Text(
                            text =
                                stringResource(R.string.untimed_title),
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier
                                .fillMaxWidth()
                                .statusBarsPadding()
                                .padding(
                                    start = CoreSpacing.SpacingMedium,
                                    bottom = CoreSpacing.SpacingSmall,
                                ),
                            style = MaterialTheme.typography.titleLarge,
                        )
                    }
                }
            }
            items(uiState.images) { picture ->
                Button(
                    onClick = { /* TODO : jump in order to preview image in full screen clicked */ },
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier.padding(PaddingValues(CoreSpacing.SpacingMedium)),
                    shapes = ButtonShapes(RoundedCornerShape(CoreRadius.RadiusMedium), RoundedCornerShape(CoreRadius.RadiusMedium)),

                    ) {
                    Box {
                        Text(text = stringResource(R.string.loading, picture.name), modifier = Modifier.align(Alignment.Center))
                        MicroGalleryImage(
                            url = "http://92.150.239.130" + picture.lowResPath,
                            // TODO : better MicroGalleryImage to call with only a Picture (fallback to highRes etc)
                            modifier = Modifier
                                .hazeSource(state = hazeState)
                                .wrapContentHeight(),
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(PaddingValues(CoreSpacing.SpacingMedium)))
            }

    }

}