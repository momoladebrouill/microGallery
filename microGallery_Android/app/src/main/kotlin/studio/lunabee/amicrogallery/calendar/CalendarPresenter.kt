package studio.lunabee.amicrogallery.calendar

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import studio.lunabee.compose.presenter.LBPresenter
import studio.lunabee.compose.presenter.LBSimpleReducer

class CalendarPresenter : LBPresenter<CalendarUiState, CalendarNavScope, CalendarAction>() {
    override val flows: List<Flow<CalendarAction>> = emptyList()

    override fun getInitialState(): CalendarUiState = CalendarUiState.Default(5)
    override fun getReducerByState(actualState: CalendarUiState): LBSimpleReducer<CalendarUiState, CalendarNavScope, CalendarAction> {
        return CalendarReducer(viewModelScope, ::emitUserAction)
    }
    override val content: @Composable ((CalendarUiState) -> Unit) = { CalendarScreen(it) }
}
