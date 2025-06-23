package studio.lunabee.amicrogallery

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
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
                Scaffold(
                    floatingActionButtonPosition = FabPosition.Center,
                    containerColor = Color.Transparent,

                    floatingActionButton = {
                        MicroGalleryBottomBar(
                            navController = navHostController,
                        )
                    },
                ) {
                    MainNavGraph(
                        hazeState = hazeState,
                        contentPadding = it,
                        navHostController = navHostController,
                        startDestination = startDestination,
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
    applySystemBarPadding: Boolean = false,
    content: @Composable (HazeState, Modifier) -> Unit,
) {
    val hazeState = remember { HazeState() }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .hazeEffect(
                state = hazeState,
                style = HazeMaterials.ultraThin(MaterialTheme.colorScheme.primary),
            )
            .then(
                if (applySystemBarPadding) {
                    Modifier
                        .statusBarsPadding()
                        .navigationBarsPadding()
                } else {
                    Modifier
                },
            ),
    ) {
        content(hazeState, Modifier.fillMaxSize())
    }
}
