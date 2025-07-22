package studio.lunabee.amicrogallery.reorder.reducers

import kotlinx.coroutines.CoroutineScope
import studio.lunabee.amicrogallery.bottomBar.BottomBarManager
import studio.lunabee.amicrogallery.bottomBar.BottomBarViewModel
import studio.lunabee.amicrogallery.reorder.ReorderAction
import studio.lunabee.amicrogallery.reorder.ReorderAction.PutPicture
import studio.lunabee.amicrogallery.reorder.ReorderNavScope
import studio.lunabee.amicrogallery.reorder.ReorderUiState
import studio.lunabee.microgallery.android.domain.utils.derange
import studio.lunabee.compose.presenter.LBReducer
import studio.lunabee.compose.presenter.ReduceResult
import studio.lunabee.compose.presenter.asResult
import studio.lunabee.amicrogallery.utils.emptyLineMap
import studio.lunabee.compose.presenter.withSideEffect
import studio.lunabee.microgallery.android.data.MicroPicture
import studio.lunabee.microgallery.android.domain.reorder.usecase.GetPicturesShuffledUseCase

class ReorderMenuReducer(
    override val coroutineScope: CoroutineScope,
    val bottomBarViewModel: BottomBarViewModel,
    override val emitUserAction: (ReorderAction) -> Unit,
    val getPicturesShuffledUseCase: GetPicturesShuffledUseCase,
) : LBReducer<ReorderUiState.ReorderMenuUiState, ReorderUiState, ReorderNavScope, ReorderAction, ReorderAction.ReorderMenuAction>() {

    override suspend fun reduce(
        actualState: ReorderUiState.ReorderMenuUiState,
        action: ReorderAction.ReorderMenuAction,
        performNavigation: (ReorderNavScope.() -> Unit) -> Unit,
    ): ReduceResult<ReorderUiState> {
        return when (action) {
            is ReorderAction.WantToJumpToGaming -> actualState.copy(isJumpingToGame = true) withSideEffect {
                val pictures = derange(
                    getPicturesShuffledUseCase(actualState.qty),
                ).toSet()
                emitUserAction(
                    ReorderAction.JumpToGaming(
                        pictures = pictures.toSet(),
                    ),
                )
            }

            is ReorderAction.JumpToGaming -> {
                bottomBarViewModel.set(false)
                ReorderUiState.ReorderGamingUiState(
                    picturesNotPlaced = action.pictures,
                    picturesQQty = action.pictures.size,
                    putPicture = fun(index: Float, picture: MicroPicture) { emitUserAction(PutPicture(index, picture)) },
                    picturesInSlots = emptyLineMap<MicroPicture?>() + (0.0f to null),
                    pictureMap = action.pictures.associateBy { it.id },
                    jumpToPicture = { emitUserAction(ReorderAction.JumpToPicture(it)) },
                ).asResult()
            }

            is ReorderAction.SetQty -> actualState.copy(qty = action.qty).asResult()
        }
    }

    override fun filterAction(action: ReorderAction): Boolean {
        return action is ReorderAction.ReorderMenuAction
    }

    override fun filterUiState(actualState: ReorderUiState): Boolean {
        return actualState is ReorderUiState.ReorderMenuUiState
    }
}
