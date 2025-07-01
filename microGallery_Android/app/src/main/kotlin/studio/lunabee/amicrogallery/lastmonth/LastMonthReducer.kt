package studio.lunabee.amicrogallery.lastmonth

import kotlinx.coroutines.CoroutineScope
import studio.lunabee.compose.presenter.LBSingleReducer
import studio.lunabee.compose.presenter.ReduceResult
import studio.lunabee.compose.presenter.asResult
import studio.lunabee.compose.presenter.withSideEffect

class LastMonthReducer(
    override val coroutineScope: CoroutineScope,
    override val emitUserAction: (LastMonthAction) -> Unit,
) : LBSingleReducer<LastMonthUiState, LastMonthNavScope, LastMonthAction>() {
    override suspend fun reduce(
        actualState: LastMonthUiState,
        action: LastMonthAction,
        performNavigation: (LastMonthNavScope.() -> Unit) -> Unit,
    ): ReduceResult<LastMonthUiState> {
        return when (action) {
            is LastMonthAction.GotTheList -> actualState.copy(pictures = action.pictures).asResult()
            is LastMonthAction.ShowPhoto -> actualState withSideEffect {
                performNavigation { navigateToPhotoViewer(action.photoId) }
            }
        }
    }
}
