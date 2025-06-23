package studio.lunabee.amicrogallery.lastmonth

import androidx.compose.animation.ExperimentalSharedTransitionApi

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

import studio.lunabee.amicrogallery.Destination

@Serializable
object LastMonthDestination : Destination {
    @OptIn(ExperimentalSharedTransitionApi::class)
    fun composable(navGraphBuilder: NavGraphBuilder, navScope: LastMonthNavScope) {
        navGraphBuilder.composable<LastMonthDestination> {
            val presenter: LastMonthPresenter = koinViewModel()
            presenter.invoke(navScope)
        }
    }
}
