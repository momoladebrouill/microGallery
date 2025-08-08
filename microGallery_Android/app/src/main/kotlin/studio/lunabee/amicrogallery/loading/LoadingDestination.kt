package studio.lunabee.amicrogallery.loading

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel
import studio.lunabee.amicrogallery.Destination

@Serializable
object LoadingDestination : Destination {
    @OptIn(ExperimentalSharedTransitionApi::class)
    fun composable(navGraphBuilder: NavGraphBuilder, navScope: LoadingNavScope) {
        navGraphBuilder.composable<LoadingDestination> {
            val presenter: LoadingPresenter = koinViewModel()
            presenter.invoke(navScope)
        }
    }
}
