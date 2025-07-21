package studio.lunabee.amicrogallery.reorder

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import studio.lunabee.amicrogallery.bottomBar.BottomBarManager
import studio.lunabee.amicrogallery.reorder.reducers.ReorderGamingReducer
import studio.lunabee.amicrogallery.reorder.reducers.ReorderMenuReducer

import studio.lunabee.compose.presenter.LBPresenter
import studio.lunabee.compose.presenter.LBSimpleReducer
import studio.lunabee.microgallery.android.domain.reorder.usecase.GetPicturesShuffledUseCase

class ReorderPresenter(
    val getPicturesShuffledUseCase: GetPicturesShuffledUseCase,
    private val bottomBarManager: BottomBarManager,
) : LBPresenter<ReorderUiState, ReorderNavScope, ReorderAction>() {

    override val flows: List<Flow<ReorderAction>> = listOf()

    override fun getInitialState(): ReorderUiState = ReorderUiState.ReorderMenuUiState(
        qty = 3,
        time = 0,
        isJumpingToGame = false,
        jumpToGaming = { emitUserAction(ReorderAction.WantToJumpToGaming) },
        setQty = { emitUserAction(ReorderAction.SetQty(it)) },
    )

    override fun getReducerByState(actualState: ReorderUiState): LBSimpleReducer<ReorderUiState, ReorderNavScope, ReorderAction> {
        return when (actualState) {
            is ReorderUiState.ReorderGamingUiState -> ReorderGamingReducer(
                coroutineScope = viewModelScope,
                emitUserAction = ::emitUserAction,
            )
            is ReorderUiState.ReorderMenuUiState -> ReorderMenuReducer(
                coroutineScope = viewModelScope,
                emitUserAction = ::emitUserAction,
                getPicturesShuffledUseCase = getPicturesShuffledUseCase,
                bottomBarManager = bottomBarManager,
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override val content: @Composable (ReorderUiState) -> Unit = { ReorderScreen(it) }
}
