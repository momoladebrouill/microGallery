package studio.lunabee.amicrogallery.calendar

import androidx.compose.animation.ExperimentalSharedTransitionApi

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.chrisbanes.haze.HazeState
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

import studio.lunabee.amicrogallery.Destination

@Serializable
object CalendarDestination : Destination {
    @OptIn(ExperimentalSharedTransitionApi::class)
    fun composable(navGraphBuilder: NavGraphBuilder, navScope: CalendarNavScope, hazeState: HazeState) {
        navGraphBuilder.composable<CalendarDestination> {
            val presenter: CalendarPresenter = koinViewModel()
            presenter.hazeState = hazeState
            presenter.invoke(navScope)
        }
    }
}
