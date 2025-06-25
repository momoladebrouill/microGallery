package studio.lunabee.amicrogallery

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import kotlin.reflect.KClass

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MainNavGraph(
    navHostController: NavHostController,
    startDestination: KClass<*>,
){
    val navController = rememberNavController()
    Box(modifier = Modifier.fillMaxSize()) {
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
                            //navHostController.navigate(LastMonthDestination("vinculo"))
                            navHostController.navigate(DashboardDestination)
                        }
                    },
                )

                LastMonthDestination.composable(
                    navGraphBuilder = this,
                    navScope = object : LastMonthNavScope {

                    }
                )
            }
        }
    }
}