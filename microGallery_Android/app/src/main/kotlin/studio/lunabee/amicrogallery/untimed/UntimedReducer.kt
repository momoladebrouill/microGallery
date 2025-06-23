package studio.lunabee.amicrogallery.untimed

import kotlinx.coroutines.CoroutineScope
import studio.lunabee.compose.presenter.LBReducer
import studio.lunabee.compose.presenter.ReduceResult
import studio.lunabee.compose.presenter.asResult

class UntimedReducer(
    override val coroutineScope: CoroutineScope,
    override val emitUserAction: (UntimedAction) -> Unit,
) : LBReducer<UntimedUiState.Default, UntimedUiState, UntimedNavScope, UntimedAction, UntimedAction.EmptyAction> () {
    override fun filterAction(action: UntimedAction): Boolean {
        return true
    }

    override fun filterUiState(actualState: UntimedUiState): Boolean {
        return true
    }

    override suspend fun reduce(
        actualState: UntimedUiState.Default,
        action: UntimedAction.EmptyAction,
        performNavigation: (UntimedNavScope.() -> Unit) -> Unit,
    ): ReduceResult<UntimedUiState> {
        return actualState.asResult()
    }
}
