import kotlinx.coroutines.CoroutineScope
import studio.lunabee.amicrogallery.loading.LoadingAction
import studio.lunabee.amicrogallery.loading.LoadingAction.Error
import studio.lunabee.amicrogallery.loading.LoadingAction.FetchingAction
import studio.lunabee.amicrogallery.loading.LoadingAction.FoundAll
import studio.lunabee.amicrogallery.loading.LoadingNavScope
import studio.lunabee.amicrogallery.loading.LoadingUiState
import studio.lunabee.compose.presenter.LBReducer
import studio.lunabee.compose.presenter.ReduceResult
import studio.lunabee.compose.presenter.asResult
import studio.lunabee.compose.presenter.withSideEffect

class FetchingReducer(
    override val coroutineScope: CoroutineScope,
    override val emitUserAction: (LoadingAction) -> Unit,
) : LBReducer<LoadingUiState.Fetching, LoadingUiState, LoadingNavScope, LoadingAction, FetchingAction>() {
    override suspend fun reduce(
        actualState: LoadingUiState.Fetching,
        action: FetchingAction,
        performNavigation: (LoadingNavScope.() -> Unit) -> Unit,
    ): ReduceResult<LoadingUiState> {
        return when (action) {
            FoundAll -> actualState withSideEffect {
                performNavigation { navigateToDashboard() }
            }

            is Error -> LoadingUiState.Error(
                errorMessage = action.errorMessage,
                reload = { },
            ).asResult()
        }
    }

    override fun filterAction(action: LoadingAction): Boolean {
        return action is FetchingAction
    }

    override fun filterUiState(actualState: LoadingUiState): Boolean {
        return actualState is LoadingUiState.Fetching
    }
}
