package studio.lunabee.amicrogallery

import android.util.Log
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import studio.lunabee.amicrogallery.calendar.CalendarDestination
import studio.lunabee.amicrogallery.calendar.CalendarNavScope
import studio.lunabee.amicrogallery.lastmonth.LastMonthDestination
import studio.lunabee.amicrogallery.lastmonth.LastMonthNavScope
import studio.lunabee.amicrogallery.untimed.UntimedDestination
import studio.lunabee.amicrogallery.untimed.UntimedNavScope
import kotlin.reflect.KClass

private const val TAG = "MainNavGraph"

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MainNavGraph(
    contentPadding: PaddingValues,
    navHostController: NavHostController,
    startDestination: KClass<*>,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        SharedTransitionLayout {
            NavHost(
                modifier = Modifier.padding(contentPadding),
                navController = navHostController,
                startDestination = startDestination,
            ) {
                CalendarDestination.composable(
                    navGraphBuilder = this,
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
    }
}
