package studio.lunabee.amicrogallery.reorder

import androidx.compose.runtime.Composable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.flow.Flow
import studio.lunabee.compose.presenter.LBSinglePresenter
import studio.lunabee.compose.presenter.LBSingleReducer

class ReorderPresenter(
    savedStateHandle: SavedStateHandle,
) : LBSinglePresenter<ReorderUiState, ReorderNavScope, ReorderAction>() {

    private val params: ReorderDestination = savedStateHandle.toRoute()

    override val flows: List<Flow<ReorderAction>> = listOf()

    override fun initReducer(): LBSingleReducer<ReorderUiState, ReorderNavScope, ReorderAction> {
        return ReorderReducer(
            coroutineScope = viewModelScope,
            emitUserAction = ::emitUserAction
        )
    }

    override fun getInitialState(): ReorderUiState = ReorderUiState

    override val content: @Composable (ReorderUiState) -> Unit = { ReorderScreen(it) }
}