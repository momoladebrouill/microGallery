package studio.lunabee.amicrogallery.dashboard

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import studio.lunabee.amicrogallery.Destination

@Serializable
object DashboardDestination : Destination {
    @OptIn(ExperimentalSharedTransitionApi::class)
    fun composable(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
    ) {
        navGraphBuilder.composable<DashboardDestination> {
            DashboardRoute(navController)
        }
    }
}
