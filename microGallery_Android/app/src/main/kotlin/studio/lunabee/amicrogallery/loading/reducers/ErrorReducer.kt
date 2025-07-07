import kotlinx.coroutines.CoroutineScope
import studio.lunabee.amicrogallery.loading.LoadingAction
import studio.lunabee.amicrogallery.loading.LoadingNavScope
import studio.lunabee.amicrogallery.loading.LoadingUiState
import studio.lunabee.compose.presenter.LBReducer
import studio.lunabee.compose.presenter.ReduceResult
import studio.lunabee.compose.presenter.asResult

class ErrorReducer(
    override val coroutineScope: CoroutineScope,
    override val emitUserAction: (LoadingAction) -> Unit,
) : LBReducer<LoadingUiState.Error, LoadingUiState, LoadingNavScope, LoadingAction, LoadingAction.ErrorAction>() {
    override suspend fun reduce(actualState: LoadingUiState.Error,
        action: LoadingAction.ErrorAction,
        performNavigation: (LoadingNavScope.() -> Unit) -> Unit): ReduceResult<LoadingUiState> {
        return actualState.asResult()
    }

    override fun filterAction(action: LoadingAction): Boolean {
        return action is LoadingAction.FetchingAction
    }

    override fun filterUiState(actualState: LoadingUiState): Boolean {
        return actualState is LoadingUiState.Fetching
    }

}
