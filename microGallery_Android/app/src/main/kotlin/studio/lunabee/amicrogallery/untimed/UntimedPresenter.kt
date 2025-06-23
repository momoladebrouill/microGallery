package studio.lunabee.amicrogallery.untimed

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import studio.lunabee.compose.presenter.LBPresenter
import studio.lunabee.compose.presenter.LBSimpleReducer

class UntimedPresenter : LBPresenter<UntimedUiState, UntimedNavScope, UntimedAction>() {
    override val flows: List<Flow<UntimedAction>> = emptyList()

    override fun getInitialState(): UntimedUiState = UntimedUiState.Default(5)
    override fun getReducerByState(actualState: UntimedUiState): LBSimpleReducer<UntimedUiState, UntimedNavScope, UntimedAction> {
        return UntimedReducer(viewModelScope, ::emitUserAction)
    }
    override val content: @Composable ((UntimedUiState) -> Unit) = { UntimedScreen(it) }
}
