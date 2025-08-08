import kotlinx.coroutines.CoroutineScope
import studio.lunabee.amicrogallery.loading.LoadingAction
import studio.lunabee.amicrogallery.loading.LoadingNavScope
import studio.lunabee.amicrogallery.loading.LoadingUiState
import studio.lunabee.compose.presenter.LBReducer
import studio.lunabee.compose.presenter.ReduceResult
import studio.lunabee.compose.presenter.asResult
import studio.lunabee.compose.presenter.withSideEffect

class FetchingReducer(
    override val coroutineScope: CoroutineScope,
    override val emitUserAction: (LoadingAction) -> Unit,
) : LBReducer<LoadingUiState.Fetching, LoadingUiState, LoadingNavScope, LoadingAction, LoadingAction.FetchingAction>() {
    override suspend fun reduce(
        actualState: LoadingUiState.Fetching,
        action: LoadingAction.FetchingAction,
        performNavigation: (LoadingNavScope.() -> Unit) -> Unit,
    ): ReduceResult<LoadingUiState> {
        return when (action) {
            LoadingAction.FoundAll -> actualState withSideEffect {
                performNavigation { navigateToDashboard() }
            }

            is LoadingAction.JumpToSettings -> actualState withSideEffect {
                performNavigation { navigateToSettings() }
            }

            is LoadingAction.Error -> LoadingUiState.Error(
                errorMessage = action.errorMessage,
                reload = { emitUserAction(LoadingAction.Restart) },
                jumpToSettings = actualState.jumpToSettings,
            ).asResult()

            is LoadingAction.FoundYear -> actualState.copy(years = action.years).asResult()
        }
    }

    override fun filterAction(action: LoadingAction): Boolean {
        return action is LoadingAction.FetchingAction
    }

    override fun filterUiState(actualState: LoadingUiState): Boolean {
        return actualState is LoadingUiState.Fetching
    }
}
