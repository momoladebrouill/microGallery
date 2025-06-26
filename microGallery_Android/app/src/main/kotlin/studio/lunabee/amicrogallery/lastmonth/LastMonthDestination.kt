package studio.lunabee.amicrogallery.lastmonth

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

@Serializable
data class LastMonthDestination(
    val link: String
) {
    //companion object {
        fun composable(navGraphBuilder: NavGraphBuilder, navScope: LastMonthNavScope) {
            navGraphBuilder.composable<LastMonthDestination> {
                val presenter: LastMonthPresenter = koinViewModel()
                presenter.invoke(navScope)
            }
        }
    //}
}