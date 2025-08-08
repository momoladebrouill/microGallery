import kotlinx.coroutines.CoroutineScope
import studio.lunabee.amicrogallery.loading.LoadingAction
import studio.lunabee.amicrogallery.loading.LoadingNavScope
import studio.lunabee.amicrogallery.loading.LoadingUiState
import studio.lunabee.compose.presenter.LBReducer
import studio.lunabee.compose.presenter.ReduceResult
import studio.lunabee.compose.presenter.asResult
import studio.lunabee.compose.presenter.withSideEffect

class ErrorReducer(
    override val coroutineScope: CoroutineScope,
    override val emitUserAction: (LoadingAction) -> Unit,
) : LBReducer<LoadingUiState.Error, LoadingUiState, LoadingNavScope, LoadingAction, LoadingAction.ErrorAction>() {
    override suspend fun reduce(
        actualState: LoadingUiState.Error,
        action: LoadingAction.ErrorAction,
        performNavigation: (LoadingNavScope.() -> Unit) -> Unit,
    ): ReduceResult<LoadingUiState> {
        return when (action) {
            LoadingAction.JumpToSettings -> actualState withSideEffect {
                performNavigation { navigateToSettings() }
            }

            LoadingAction.Restart -> LoadingUiState.Fetching(
                years = emptyList(),
                jumpToSettings = actualState.jumpToSettings,
            ).asResult()
        }
    }

    override fun filterAction(action: LoadingAction): Boolean {
        return action is LoadingAction.ErrorAction
    }

    override fun filterUiState(actualState: LoadingUiState): Boolean {
        return actualState is LoadingUiState.Error
    }
}
