package studio.lunabee.amicrogallery.dashboard

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
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

@Composable
fun DashboardRoute(navController: NavHostController) {
    DashboardScreen(
        navHostController = navController,
        startDestination = CalendarDestination::class,
    )
}

val LocalBottomBarHeight = compositionLocalOf<Int> { error("No value found !") }

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun DashboardScreen(
    navHostController: NavHostController,
    startDestination: KClass<*>,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        var barHeight by remember { mutableIntStateOf(0) }

        CompositionLocalProvider(LocalBottomBarHeight provides barHeight) {
            SharedTransitionLayout {
                NavHost(
                    navController = navHostController,
                    startDestination = startDestination,
                ) {
                    PhotoViewerDestination().composable(
                        navGraphBuilder = this,
                        navScope = object : PhotoViewerNavScope {},
                    )

                    CalendarDestination.composable(
                        navGraphBuilder = this,
                        navScope = object : CalendarNavScope {

                            override val navigateToSettings: () -> Unit = { navHostController.navigate(SettingsDestination) }

                            override val navigateToPhotoViewer: (Long) -> Unit = { photoId: Long ->
                                navHostController.navigate(PhotoViewerDestination(photoId))
                            }
                        },
                    )
                    UntimedDestination.composable(
                        navGraphBuilder = this,
                        navScope = object : UntimedNavScope {
                            override val navigateToPhotoViewer: (Long) -> Unit = { photoId: Long ->
                                navHostController.navigate(PhotoViewerDestination(photoId))
                            }
                        },
                    )
                    LastMonthDestination.composable(
                        navGraphBuilder = this,
                        navScope = object : LastMonthNavScope {
                            override val navigateToPhotoViewer: (Long) -> Unit = { photoId: Long ->
                                navHostController.navigate(PhotoViewerDestination(photoId))
                            }
                        },
                    )
                    SettingsDestination.composable(
                        navGraphBuilder = this,
                        navScope = object : SettingsNavScope {
                            override fun jumpBack() {
                                navHostController.navigateUp()
                            }
                        },
                    )
                }
            }
            MicroGalleryBottomBar(
                navController = navHostController,
                modifier = Modifier
                    .align(alignment = Alignment.BottomCenter)
                    .onGloballyPositioned { coordinates ->
                        barHeight = coordinates.size.height / 2
                    },
            )
        }
    }
}
