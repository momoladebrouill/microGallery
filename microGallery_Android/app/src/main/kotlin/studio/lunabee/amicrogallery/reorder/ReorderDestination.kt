package studio.lunabee.amicrogallery.reorder

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

@Serializable
data object ReorderDestination {
    @OptIn(ExperimentalSharedTransitionApi::class)
    fun composable(
        navGraphBuilder: NavGraphBuilder,
        navScope: ReorderNavScope
    ) {
        navGraphBuilder.composable<ReorderDestination> {
            val presenter: ReorderPresenter = koinViewModel()
            presenter.invoke(navScope)
        }
    }
}
