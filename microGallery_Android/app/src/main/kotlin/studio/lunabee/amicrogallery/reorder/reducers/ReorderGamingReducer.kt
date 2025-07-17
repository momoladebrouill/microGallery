package studio.lunabee.amicrogallery.reorder.reducers

import kotlinx.coroutines.CoroutineScope
import studio.lunabee.amicrogallery.reorder.ReorderAction
import studio.lunabee.amicrogallery.reorder.ReorderNavScope
import studio.lunabee.amicrogallery.reorder.ReorderUiState
import studio.lunabee.compose.presenter.LBReducer
import studio.lunabee.compose.presenter.LBSingleReducer
import studio.lunabee.compose.presenter.ReduceResult
import studio.lunabee.compose.presenter.asResult
import kotlin.collections.get

class ReorderGamingReducer(
    override val coroutineScope: CoroutineScope,
    override val emitUserAction: (ReorderAction) -> Unit,
) : LBReducer<ReorderUiState.ReorderGamingUiState, ReorderUiState, ReorderNavScope, ReorderAction, ReorderAction.ReorderGamingAction>() {
    override suspend fun reduce(
        actualState: ReorderUiState.ReorderGamingUiState,
        action: ReorderAction.ReorderGamingAction,
        performNavigation: (ReorderNavScope.() -> Unit) -> Unit,
    ): ReduceResult<ReorderUiState> {
        when (action) {
            is ReorderAction.PutPicture -> {
                val picMap = actualState.picturesInSlots
                val oldIndex: Float? = picMap.getByValue(action.url) // where the picture was (null if in waiting drawer)

                return if (oldIndex == null) {
                    actualState.copy(
                        picturesNotPlaced = actualState.picturesNotPlaced - action.url, // remove the newly placed object from the list
                        picturesInSlots = picMap - null + (picMap.justAfter(action.index) to action.url),
                    ).asResult() // remove null whom served as placeholder
                } else {
                    if (picMap.areFollowing(action.index, oldIndex) || action.index == oldIndex) {
                        // switch places
                        actualState.copy(
                            picturesInSlots = picMap + (action.index to action.url) + (oldIndex to picMap[action.index]),
                        ).asResult()
                    } else {
                        actualState.copy( // remove from where it was and place it just after were the user wanted to put it
                            picturesInSlots = picMap - action.url + (picMap.justAfter(action.index) to action.url),
                        ).asResult()
                    }
                }
            }
        }
    }

    override fun filterAction(action: ReorderAction): Boolean {
        return action is ReorderAction.ReorderGamingAction
    }

    override fun filterUiState(actualState: ReorderUiState): Boolean {
        return actualState is ReorderUiState.ReorderGamingUiState
    }
}