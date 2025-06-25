package studio.lunabee.amicrogallery.calendar

import kotlinx.coroutines.CoroutineScope
import studio.lunabee.compose.presenter.LBSingleReducer
import studio.lunabee.compose.presenter.ReduceResult
import studio.lunabee.compose.presenter.asResult

class CalendarReducer(

    override val coroutineScope: CoroutineScope,
    override val emitUserAction: (CalendarAction) -> Unit,
) : LBSingleReducer<CalendarUiState, CalendarNavScope, CalendarAction> () {

    override suspend fun reduce(
        actualState: CalendarUiState,
        action: CalendarAction,
        performNavigation: (CalendarNavScope.() -> Unit) -> Unit,
    ): ReduceResult<CalendarUiState> {
        return when (action) {
            is CalendarAction.StopRefreshing -> actualState.copy(rootNode = action.foundNode).asResult()
        }
    }
}
