package studio.lunabee.amicrogallery

import android.util.Log
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import dev.chrisbanes.haze.HazeState
import studio.lunabee.amicrogallery.calendar.CalendarDestination
import studio.lunabee.amicrogallery.calendar.CalendarNavScope
import studio.lunabee.amicrogallery.lastmonth.LastMonthDestination
import studio.lunabee.amicrogallery.lastmonth.LastMonthNavScope
import studio.lunabee.amicrogallery.settings.SettingsDestination
import studio.lunabee.amicrogallery.settings.SettingsNavScope
import studio.lunabee.amicrogallery.untimed.UntimedDestination
import studio.lunabee.amicrogallery.untimed.UntimedNavScope
import kotlin.reflect.KClass

private const val TAG = "MainNavGraph"

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MainNavGraph(
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
                CalendarDestination.composable(
                    navGraphBuilder = this,
                    hazeState = hazeState,
                    navScope = object : CalendarNavScope {
                        override val navigateToSettings: () -> Unit
                            get() = { navHostController.navigate(SettingsDestination) }
                    },
                )
                UntimedDestination.composable(
                    navGraphBuilder = this,
                    navScope = object : UntimedNavScope {},
                )
                LastMonthDestination.composable(
                    navGraphBuilder = this,
                    navScope = object : LastMonthNavScope {},
                )
                SettingsDestination.composable(
                    navGraphBuilder = this,
                    navScope = object : SettingsNavScope {
                        override fun jumpBack() {
                            navHostController.popBackStack()
                        }
                    },
                )
            }
        }
    }
}
