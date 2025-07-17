package studio.lunabee.amicrogallery.reorder

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import studio.lunabee.amicrogallery.reorder.reducers.ReorderGamingReducer
import studio.lunabee.amicrogallery.reorder.reducers.ReorderMenuReducer

import studio.lunabee.compose.presenter.LBPresenter
import studio.lunabee.compose.presenter.LBSimpleReducer

class ReorderPresenter : LBPresenter<ReorderUiState, ReorderNavScope, ReorderAction>() {

    override val flows: List<Flow<ReorderAction>> = listOf()



    override fun getInitialState(): ReorderUiState = ReorderUiState.ReorderMenuUiState(
        pictures = emptySet(),
        time = 0,
        jumpToGaming = {emitUserAction(ReorderAction.JumpToGaming)},
    )

    override fun getReducerByState(actualState: ReorderUiState): LBSimpleReducer<ReorderUiState, ReorderNavScope, ReorderAction> {
        return when(actualState){
            is ReorderUiState.ReorderGamingUiState -> ReorderGamingReducer(
                coroutineScope = viewModelScope,
                emitUserAction = ::emitUserAction
            )
            is ReorderUiState.ReorderMenuUiState -> ReorderMenuReducer(
                coroutineScope = viewModelScope,
                emitUserAction = ::emitUserAction
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override val content: @Composable (ReorderUiState) -> Unit = { ReorderScreen(it) }
}
