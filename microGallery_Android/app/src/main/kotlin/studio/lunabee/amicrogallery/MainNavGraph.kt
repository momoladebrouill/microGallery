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
import androidx.navigation.compose.rememberNavController
import studio.lunabee.amicrogallery.dashboard.DashboardDestination
import studio.lunabee.amicrogallery.lastmonth.LastMonthDestination
import studio.lunabee.amicrogallery.lastmonth.LastMonthNavScope
import studio.lunabee.amicrogallery.loading.LoadingDestination
import studio.lunabee.amicrogallery.loading.LoadingNavScope
import studio.lunabee.amicrogallery.settings.SettingsDestination
import studio.lunabee.amicrogallery.settings.SettingsNavScope
import studio.lunabee.amicrogallery.untimed.UntimedDestination
import studio.lunabee.amicrogallery.untimed.UntimedNavScope
import kotlin.reflect.KClass

private const val TAG = "MainNavGraph"

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MainNavGraph(
    navHostController: NavHostController,
    startDestination: KClass<*>,
) {
    val navController = rememberNavController()
    SharedTransitionLayout {
        NavHost(
            navController = navHostController,
            startDestination = startDestination,
        ) {

            DashboardDestination.composable(
                navGraphBuilder = this,
                navController = navController
            )

            LoadingDestination.composable(
                navGraphBuilder = this,
                navScope = object : LoadingNavScope {
                    override val navigateToDashboard = {
                        navHostController.navigate(DashboardDestination)
                    }
                },
            )


        }
    }
}

