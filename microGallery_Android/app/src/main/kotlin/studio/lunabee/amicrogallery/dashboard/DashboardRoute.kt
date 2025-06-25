package studio.lunabee.amicrogallery.dashboard

import android.annotation.SuppressLint
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
        startDestination = CalendarDestination::class
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
                CalendarDestination.composable(
                    navGraphBuilder = this,
                    hazeState = hazeState,
                    navScope = object : CalendarNavScope {
                        override val navigateToMicroYear = { year: Int ->
                            Log.d(TAG, "must navigate to year $year")
                            Unit
                        }
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
            }
        }
        MicroGalleryBottomBar(
            navController = navHostController,
            modifier = Modifier.align(alignment = Alignment.BottomCenter).navigationBarsPadding(),
        )
    }
}