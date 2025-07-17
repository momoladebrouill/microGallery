package studio.lunabee.amicrogallery.reorder.reducers

import kotlinx.coroutines.CoroutineScope
import studio.lunabee.amicrogallery.reorder.ReorderAction
import studio.lunabee.amicrogallery.reorder.ReorderAction.PutPicture
import studio.lunabee.amicrogallery.reorder.ReorderNavScope
import studio.lunabee.amicrogallery.reorder.ReorderUiState
import studio.lunabee.amicrogallery.utils.derange
import studio.lunabee.compose.presenter.LBReducer
import studio.lunabee.compose.presenter.ReduceResult
import studio.lunabee.compose.presenter.asResult
import studio.lunabee.amicrogallery.utils.emptyLineMap

class ReorderMenuReducer(
    override val coroutineScope: CoroutineScope,
    override val emitUserAction: (ReorderAction) -> Unit
) : LBReducer<ReorderUiState.ReorderMenuUiState, ReorderUiState, ReorderNavScope, ReorderAction, ReorderAction.ReorderMenuAction>() {


    override suspend fun reduce(actualState: ReorderUiState.ReorderMenuUiState,
        action: ReorderAction.ReorderMenuAction,
        performNavigation: (ReorderNavScope.() -> Unit) -> Unit): ReduceResult<ReorderUiState> {
        return when(action){
            is ReorderAction.JumpToGaming -> ReorderUiState.ReorderGamingUiState(
                picturesNotPlaced = derange(
                    arrayOf(
                        "https://services.google.com/fh/files/misc/qq10.jpeg",
                        "https://services.google.com/fh/files/misc/qq9.jpeg",
                        "https://services.google.com/fh/files/misc/qq8.jpeg",
                        "https://pethelpful.com/.image/w_3840,q_auto:good,c_fill,ar_4:3/MTk2NzY3MjA5ODc0MjY5ODI2/top-10-cutest-cat-photos-of-all-time.jpg",
                        "https://i.ytimg.com/vi/vH8kYVahdrU/maxresdefault.jpg",
                    ),
                ).toSet(),
                picturesQQty = 5,
                putPicture = fun(index: Float, url: String) { emitUserAction(PutPicture(index, url)) },
                picturesInSlots = emptyLineMap<String?>() + (0.0f to null)
            ).asResult()

        }
    }

    override fun filterAction(action: ReorderAction): Boolean {
        return action is ReorderAction.ReorderMenuAction
    }

    override fun filterUiState(actualState: ReorderUiState): Boolean {
        return actualState is ReorderUiState.ReorderMenuUiState
    }

}