package studio.lunabee.amicrogallery.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonShapes
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
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
import studio.lunabee.microgallery.android.domain.Directory
import studio.lunabee.microgallery.android.domain.Node
import studio.lunabee.microgallery.android.domain.Picture

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalHazeMaterialsApi::class)
@Composable
fun CalendarScreen(calendarUiState: CalendarUiState, hazeState: HazeState?) {
    if (calendarUiState.rootNode == null) {
        LoadingScreen()
    } else {
        LazyColumn(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
        ) {
            val rootDir: Directory = calendarUiState.rootNode as Directory
            for (yearNode in rootDir.content) {
                stickyHeader {
                    Box(
                        modifier = Modifier.background(Color.Transparent)
                            .hazeEffect(
                                state = hazeState!!,
                                style = HazeMaterials.ultraThin(MaterialTheme.colorScheme.primary),
                            ),
                    ) {
                        Text(
                            text = yearNode.name,
                            color = MaterialTheme.colorScheme.onSecondary,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    top = CoreSpacing.SpacingMedium,
                                    start = CoreSpacing.SpacingMedium,
                                    bottom = CoreSpacing.SpacingSmall,
                                ),
                            style = MaterialTheme.typography.headlineLargeEmphasized,
                        )
                    }
                }
                val yearDir: Directory = yearNode as Directory
                items(yearDir.content) { monthNode ->
                    ShowNode(monthNode)
                    val monthDir: Directory = monthNode as Directory
                    for (picture in monthDir.content) {
                        ShowNode(picture, modifier = Modifier.hazeSource(state = hazeState!!))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ShowNode(node: Node, modifier: Modifier = Modifier) {
    when (node) {
        is Directory -> {
            Text(
                text = getLabelName(node.name),
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = CoreSpacing.SpacingMedium, top = CoreSpacing.SpacingMedium, bottom = CoreSpacing.SpacingSmall),
                style = MaterialTheme.typography.headlineMedium,
            )
        }
        is Picture -> {
            Button(
                onClick = { /* TODO : jump to preview image clicked */ },
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.padding(PaddingValues(CoreSpacing.SpacingMedium)),
                shapes = ButtonShapes(RoundedCornerShape(CoreRadius.RadiusMedium), RoundedCornerShape(CoreRadius.RadiusMedium)),

            ) {
                Box {
                    Text(text = stringResource(R.string.loading, node.name), modifier = Modifier.align(Alignment.Center))
                    MicroGalleryImage(
                        // TOOD : fallback to highRes if lowRes not found
                        url = "http://92.150.239.130" + node.lowResPath,
                        modifier = modifier.fillMaxWidth().wrapContentHeight(),
                    )
                }
            }
            Spacer(modifier = Modifier.padding(PaddingValues(CoreSpacing.SpacingMedium)))
        }
    }
}

@Composable
fun getLabelName(value: String): String {
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

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun LoadingScreen() {
    Column {
        Text(
            text = stringResource(R.string.waitingForData),
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            text = stringResource(R.string.notLong),
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}
