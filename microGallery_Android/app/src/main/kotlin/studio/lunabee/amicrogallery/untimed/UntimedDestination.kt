package studio.lunabee.amicrogallery.untimed 

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel
import studio.lunabee.amicrogallery.Destination

@Serializable
data object UntimedDestination : Destination {
    fun composable(navGraphBuilder: NavGraphBuilder, navScope: UntimedNavScope) {
        navGraphBuilder.composable<UntimedDestination> {
            val presenter: UntimedPresenter = koinViewModel()
            presenter.invoke(navScope)
        }
    }
}