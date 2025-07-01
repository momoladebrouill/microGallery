package studio.lunabee.amicrogallery

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import studio.lunabee.amicrogallery.dashboard.DashboardDestination
import studio.lunabee.amicrogallery.loading.LoadingDestination
import studio.lunabee.amicrogallery.loading.LoadingNavScope
import kotlin.reflect.KClass

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
                navController = navController,
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
