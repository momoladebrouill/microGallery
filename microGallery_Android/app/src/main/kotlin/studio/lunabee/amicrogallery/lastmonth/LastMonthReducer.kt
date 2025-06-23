package studio.lunabee.amicrogallery.lastmonth

import kotlinx.coroutines.CoroutineScope
import studio.lunabee.compose.presenter.LBReducer
import studio.lunabee.compose.presenter.ReduceResult
import studio.lunabee.compose.presenter.asResult

class LastMonthReducer(
    override val coroutineScope: CoroutineScope,
    override val emitUserAction: (LastMonthAction) -> Unit,
) : LBReducer<LastMonthUiState.Default, LastMonthUiState, LastMonthNavScope, LastMonthAction, LastMonthAction.EmptyAction> () {
    override fun filterAction(action: LastMonthAction): Boolean {
        return true
    }

    override fun filterUiState(actualState: LastMonthUiState): Boolean {
        return true
    }

    override suspend fun reduce(
        actualState: LastMonthUiState.Default,
        action: LastMonthAction.EmptyAction,
        performNavigation: (LastMonthNavScope.() -> Unit) -> Unit,
    ): ReduceResult<LastMonthUiState> {
        return actualState.asResult()
    }
}
