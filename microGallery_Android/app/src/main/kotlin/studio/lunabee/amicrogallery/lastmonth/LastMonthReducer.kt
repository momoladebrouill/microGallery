package studio.lunabee.amicrogallery.lastmonth

import kotlinx.coroutines.CoroutineScope
import studio.lunabee.compose.presenter.LBSingleReducer
import studio.lunabee.compose.presenter.ReduceResult
import studio.lunabee.compose.presenter.asResult

class LastMonthReducer(
    override val coroutineScope: CoroutineScope,
    override val emitUserAction: (LastMonthAction) -> Unit,
) : LBSingleReducer<LastMonthUiState, LastMonthNavScope, LastMonthAction>() {
    override suspend fun reduce(
        actualState: LastMonthUiState,
        action: LastMonthAction,
        performNavigation: (LastMonthNavScope.() -> Unit) -> Unit,
    ): ReduceResult<LastMonthUiState> {
        return actualState.asResult()
    }
}