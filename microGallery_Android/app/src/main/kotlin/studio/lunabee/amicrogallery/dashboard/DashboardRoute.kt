package studio.lunabee.amicrogallery.dashboard

import android.util.Log
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import dev.chrisbanes.haze.HazeState
import studio.lunabee.amicrogallery.bottomBar.MicroGalleryBottomBar
import studio.lunabee.amicrogallery.calendar.CalendarDestination
import studio.lunabee.amicrogallery.calendar.CalendarNavScope
import studio.lunabee.amicrogallery.lastmonth.LastMonthDestination
import studio.lunabee.amicrogallery.lastmonth.LastMonthNavScope
import studio.lunabee.amicrogallery.photoviewer.PhotoViewerDestination
import studio.lunabee.amicrogallery.photoviewer.PhotoViewerNavScope
import studio.lunabee.amicrogallery.settings.SettingsDestination
import studio.lunabee.amicrogallery.settings.SettingsNavScope
import studio.lunabee.amicrogallery.untimed.UntimedDestination
import studio.lunabee.amicrogallery.untimed.UntimedNavScope
import kotlin.reflect.KClass

private const val TAG = "MainNavGraph"

@Composable
fun DashboardRoute(navController: NavHostController) {
    val hazeState = remember { HazeState() }
    DashboardScreen(
        hazeState = hazeState,
        navHostController = navController,
        startDestination = CalendarDestination::class,
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)

@Composable
fun DashboardScreen(
    hazeState: HazeState,
    navHostController: NavHostController,
    startDestination: KClass<*>,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        SharedTransitionLayout {
            NavHost(
                navController = navHostController,
                startDestination = startDestination,
            ) {

                PhotoViewerDestination(pictureId = 0).composable(
                    navGraphBuilder = this,
                    navScope = object : PhotoViewerNavScope {}
                )

                CalendarDestination.composable(
                    navGraphBuilder = this,
                    hazeState = hazeState,
                    navScope = object : CalendarNavScope {

                        override val navigateToSettings: () -> Unit = { navHostController.navigate(SettingsDestination) }

                        override val navigateToPhotoViewer: (Long) -> Unit = { photoId : Long ->
                            navHostController.navigate(PhotoViewerDestination(photoId))
                        }
                    },
                )
                UntimedDestination.composable(
                    navGraphBuilder = this,
                    navScope = object : UntimedNavScope {
                        override val navigateToPhotoViewer: (Long) -> Unit = { photoId : Long ->
                            navHostController.navigate(PhotoViewerDestination(photoId))
                        }
                    },
                )
                LastMonthDestination.composable(
                    navGraphBuilder = this,
                    navScope = object : LastMonthNavScope {
                        override val navigateToPhotoViewer: (Long) -> Unit = { photoId : Long ->
                            navHostController.navigate(PhotoViewerDestination(photoId))
                        }
                    },
                )
                SettingsDestination.composable(
                    navGraphBuilder = this,
                    navScope = object : SettingsNavScope {},
                )
            }
        }
        MicroGalleryBottomBar(
            navController = navHostController,
            modifier = Modifier.align(alignment = Alignment.BottomCenter)
        )
    }
}