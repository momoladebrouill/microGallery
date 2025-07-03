package studio.lunabee.amicrogallery.loading

import kotlinx.coroutines.CoroutineScope
import studio.lunabee.amicrogallery.loading.LoadingUiState.*
import studio.lunabee.compose.presenter.LBSingleReducer
import studio.lunabee.compose.presenter.ReduceResult
import studio.lunabee.compose.presenter.asResult
import studio.lunabee.compose.presenter.withSideEffect
import studio.lunabee.amicrogallery.app.R
class LoadingReducer(

    override val coroutineScope: CoroutineScope,
    override val emitUserAction: (LoadingAction) -> Unit,
) : LBSingleReducer<LoadingUiState, LoadingNavScope, LoadingAction> () {

    override suspend fun reduce(
        actualState: LoadingUiState,
        action: LoadingAction,
        performNavigation: (LoadingNavScope.() -> Unit) -> Unit,
    ): ReduceResult<LoadingUiState> {
        return when (action) {
            is LoadingAction.FoundData -> {
                actualState withSideEffect {
                    performNavigation { navigateToDashboard() }
                }
            }
            is LoadingAction.Error -> {
                Error(action.errorMessage).asResult()
            }
            is LoadingAction.Reload -> Default().asResult()
            LoadingAction.FoundSettings -> Default(log = R.string.found_settings).asResult()
        }
    }
}
