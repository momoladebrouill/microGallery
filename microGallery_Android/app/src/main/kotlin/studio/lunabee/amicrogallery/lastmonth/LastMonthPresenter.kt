package studio.lunabee.amicrogallery.lastmonth

import androidx.compose.runtime.Composable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.flow.Flow
import studio.lunabee.compose.presenter.LBSinglePresenter
import studio.lunabee.compose.presenter.LBSingleReducer

class LastMonthPresenter(
    savedStateHandle: SavedStateHandle,
) : LBSinglePresenter<LastMonthUiState, LastMonthNavScope, LastMonthAction>() {

    private val params: LastMonthDestination = savedStateHandle.toRoute()

    override val flows: List<Flow<LastMonthAction>> = listOf()

    override fun initReducer(): LBSingleReducer<LastMonthUiState, LastMonthNavScope, LastMonthAction> {
        return LastMonthReducer(
            coroutineScope = viewModelScope,
            emitUserAction = ::emitUserAction
        )
    }

    override fun getInitialState(): LastMonthUiState = LastMonthUiState(params.link)

    override val content: @Composable (LastMonthUiState) -> Unit = { LastMonthScreen(it) }
}