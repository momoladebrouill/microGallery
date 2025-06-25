package studio.lunabee.amicrogallery.untimed 

import androidx.compose.runtime.Composable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.flow.Flow
import studio.lunabee.compose.presenter.LBSinglePresenter
import studio.lunabee.compose.presenter.LBSingleReducer

class UntimedPresenter(
    savedStateHandle: SavedStateHandle,
) : LBSinglePresenter<UntimedUiState, UntimedNavScope, UntimedAction>() {

    private val params: UntimedDestination = savedStateHandle.toRoute()

    override val flows: List<Flow<UntimedAction>> = listOf()
    
    override fun initReducer(): LBSingleReducer<UntimedUiState, UntimedNavScope, UntimedAction> {
        return UntimedReducer(
            coroutineScope = viewModelScope,
            emitUserAction = ::emitUserAction
        )
    }
    

    override fun getInitialState(): UntimedUiState = UntimedUiState

    override val content: @Composable (UntimedUiState) -> Unit = { UntimedScreen(it) }
}