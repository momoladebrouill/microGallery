package studio.lunabee.amicrogallery.lastmonth

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import studio.lunabee.compose.presenter.LBPresenter
import studio.lunabee.compose.presenter.LBSimpleReducer

class LastMonthPresenter : LBPresenter<LastMonthUiState, LastMonthNavScope, LastMonthAction>() {
    override val flows: List<Flow<LastMonthAction>> = emptyList()

    override fun getInitialState(): LastMonthUiState = LastMonthUiState.Default(5)
    override fun getReducerByState(actualState: LastMonthUiState): LBSimpleReducer<LastMonthUiState, LastMonthNavScope, LastMonthAction> {
        return LastMonthReducer(viewModelScope, ::emitUserAction)
    }
    override val content: @Composable ((LastMonthUiState) -> Unit) = { LastMonthScreen(it) }
}
