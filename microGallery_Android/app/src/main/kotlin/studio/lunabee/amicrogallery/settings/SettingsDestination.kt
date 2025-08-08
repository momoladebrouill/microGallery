package studio.lunabee.amicrogallery.settings

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel
import studio.lunabee.amicrogallery.Destination

@Serializable
object SettingsDestination : Destination {
    @OptIn(ExperimentalSharedTransitionApi::class)
    fun composable(navGraphBuilder: NavGraphBuilder, navScope: SettingsNavScope) {
        navGraphBuilder.composable<SettingsDestination> {
            val presenter: SettingsPresenter = koinViewModel()
            presenter.invoke(navScope)
        }
    }
}
