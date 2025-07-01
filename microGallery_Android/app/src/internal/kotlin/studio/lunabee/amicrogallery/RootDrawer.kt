package studio.lunabee.amicrogallery

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import studio.lunabee.amicrogallery.loading.LoadingDestination

@Composable
fun RootDrawer(
    navHostController: NavHostController,
) {
    MicroGalleryRootScreen { modifier ->
        Box {
            MainNavGraph(
                navHostController = navHostController,
                startDestination = LoadingDestination::class,
            )
        }
    }
}

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun MicroGalleryRootScreen(
    modifier: Modifier = Modifier,
    content: @Composable (modifier: Modifier) -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        content(Modifier.fillMaxSize())
    }
}
