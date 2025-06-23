package studio.lunabee.amicrogallery.calendar

import androidx.compose.animation.ExperimentalSharedTransitionApi

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

import studio.lunabee.amicrogallery.Destination

@Serializable
object CalendarDestination : Destination {
    @OptIn(ExperimentalSharedTransitionApi::class)
    fun composable(navGraphBuilder: NavGraphBuilder, navScope: CalendarNavScope) {
        navGraphBuilder.composable<CalendarDestination> {
            val presenter: CalendarPresenter = koinViewModel()
            presenter.invoke(navScope)
        }
    }
}
