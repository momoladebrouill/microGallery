package studio.lunabee.amicrogallery.calendar

import kotlinx.coroutines.CoroutineScope
import studio.lunabee.compose.presenter.LBReducer
import studio.lunabee.compose.presenter.ReduceResult
import studio.lunabee.compose.presenter.asResult

class CalendarReducer(
    override val coroutineScope: CoroutineScope,
    override val emitUserAction: (CalendarAction) -> Unit,
) : LBReducer<CalendarUiState.Default, CalendarUiState, CalendarNavScope, CalendarAction, CalendarAction.EmptyAction> () {
    override fun filterAction(action: CalendarAction): Boolean {
        return true
    }

    override fun filterUiState(actualState: CalendarUiState): Boolean {
        return true
    }

    override suspend fun reduce(
        actualState: CalendarUiState.Default,
        action: CalendarAction.EmptyAction,
        performNavigation: (CalendarNavScope.() -> Unit) -> Unit,
    ): ReduceResult<CalendarUiState> {
        return actualState.asResult()
    }
}
