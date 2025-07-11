package studio.lunabee.amicrogallery.reorder

import kotlinx.coroutines.CoroutineScope
import studio.lunabee.compose.presenter.LBSingleReducer
import studio.lunabee.compose.presenter.ReduceResult
import studio.lunabee.compose.presenter.asResult

class ReorderReducer(
    override val coroutineScope: CoroutineScope,
    override val emitUserAction: (ReorderAction) -> Unit,
) : LBSingleReducer<ReorderUiState, ReorderNavScope, ReorderAction>() {
    override suspend fun reduce(
        actualState: ReorderUiState,
        action: ReorderAction,
        performNavigation: (ReorderNavScope.() -> Unit) -> Unit,
    ): ReduceResult<ReorderUiState> {
        return actualState.asResult()
    }
}