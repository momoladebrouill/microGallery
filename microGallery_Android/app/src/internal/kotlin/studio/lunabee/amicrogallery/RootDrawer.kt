package studio.lunabee.amicrogallery

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import studio.lunabee.amicrogallery.bottomBar.MicroGalleryBottomBar
import kotlin.reflect.KClass

@Composable
fun RootDrawer(
    navHostController: NavHostController,
    startDestination: KClass<*>,
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        content = {
            MicroGalleryRootScreen { hazeState, modifier ->
                Box {
                    MainNavGraph(
                        hazeState = hazeState,
                        navHostController = navHostController,
                        startDestination = startDestination,
                    )
                    MicroGalleryBottomBar(
                        navController = navHostController,
                        modifier = Modifier.align(alignment = Alignment.BottomCenter).navigationBarsPadding(),
                    )
                }
            }
        },
        drawerContent = { DebugMenu(navHostController = navHostController) },
    )
}

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun MicroGalleryRootScreen(
    modifier: Modifier = Modifier,
    content: @Composable (HazeState, Modifier) -> Unit,
) {
    val hazeState = remember { HazeState() }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        content(hazeState, Modifier.fillMaxSize())
    }
}
