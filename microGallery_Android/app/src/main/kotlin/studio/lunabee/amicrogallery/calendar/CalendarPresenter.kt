package studio.lunabee.amicrogallery.calendar

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import studio.lunabee.compose.presenter.LBSinglePresenter
import studio.lunabee.compose.presenter.LBSingleReducer

class CalendarPresenter : LBSinglePresenter<CalendarUiState, CalendarNavScope, CalendarAction>() {
    override val flows: List<Flow<CalendarAction>> = emptyList()

    override fun getInitialState(): CalendarUiState = CalendarUiState()

    override fun initReducer(): LBSingleReducer<CalendarUiState, CalendarNavScope, CalendarAction> {
        return CalendarReducer(viewModelScope, ::emitUserAction)
    }

    private fun onAction(action: CalendarAction) {
        viewModelScope.launch { emitUserAction(action) }
    }

    override val content: @Composable ((CalendarUiState) -> Unit) = { CalendarScreen(it, onAction = ::onAction) }
}
