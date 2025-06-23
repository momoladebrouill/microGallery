package studio.lunabee.amicrogallery

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
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
            MicroGalleryRootScreen {
                Scaffold(
                    modifier = Modifier,
                    bottomBar = {
                        MicroGalleryBottomBar(
                            navController = navHostController,
                        )
                    },
                ) {
                    MainNavGraph(
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

@Composable
fun MicroGalleryRootScreen(
    modifier: Modifier = Modifier,
    applySystemBarPadding: Boolean = true,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
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
        content()
    }
}
