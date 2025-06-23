package studio.lunabee.amicrogallery.untimed

import androidx.compose.animation.ExperimentalSharedTransitionApi

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

import studio.lunabee.amicrogallery.Destination

@Serializable
object UntimedDestination : Destination {
    @OptIn(ExperimentalSharedTransitionApi::class)
    fun composable(navGraphBuilder: NavGraphBuilder, navScope: UntimedNavScope) {
        navGraphBuilder.composable<UntimedDestination> {
            val presenter: UntimedPresenter = koinViewModel()
            presenter.invoke(navScope)
        }
    }
}
