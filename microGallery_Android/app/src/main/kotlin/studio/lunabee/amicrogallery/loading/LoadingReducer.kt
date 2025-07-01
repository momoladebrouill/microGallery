package studio.lunabee.amicrogallery.loading

import kotlinx.coroutines.CoroutineScope
import studio.lunabee.compose.presenter.LBSingleReducer
import studio.lunabee.compose.presenter.ReduceResult
import studio.lunabee.compose.presenter.asResult
import studio.lunabee.compose.presenter.withSideEffect

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
                LoadingUiState.Error(action.errorMessage).asResult()
            }
            is LoadingAction.Reload -> LoadingUiState.Default().asResult()
        }
    }
}
