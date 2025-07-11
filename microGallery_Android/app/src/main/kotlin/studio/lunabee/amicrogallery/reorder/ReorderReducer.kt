package studio.lunabee.amicrogallery.reorder

import kotlinx.coroutines.CoroutineScope
import studio.lunabee.amicrogallery.utils.LineMap
import studio.lunabee.compose.presenter.LBSingleReducer
import studio.lunabee.compose.presenter.ReduceResult
import studio.lunabee.compose.presenter.asResult

class ReorderReducer(
    override val coroutineScope: CoroutineScope,
    override val emitUserAction: (ReorderAction) -> Unit,
) : LBSingleReducer<ReorderUiState, ReorderNavScope, ReorderAction>() {
    override suspend fun reduce(
        actualState: ReorderUiState,
        action: ReorderAction,
        performNavigation: (ReorderNavScope.() -> Unit) -> Unit,
    ): ReduceResult<ReorderUiState> {
        when (action) {
            is ReorderAction.PutPicture -> {
                val picMap = actualState.picturesInSlots
                val oldIndex: Float? = picMap.getByValue(action.url) // where the picture was (null if in waiting drawer)

                return if (oldIndex == null) {
                    actualState.copy(
                        picturesNotPlaced = actualState.picturesNotPlaced - action.url, // remove the newly placed object from the list
                        picturesInSlots = picMap + (picMap.justAfter(action.index) to action.url)
                    ).asResult()
                } else {
                    if (picMap.areFollowing(action.index, oldIndex) || action.index == oldIndex)
                    // switch places
                        actualState.copy(
                            picturesInSlots = picMap + (action.index to action.url) + (oldIndex to picMap[action.index])
                        ).asResult()
                    else
                        actualState.copy( // remove from where it was and place it just after were the user wanted to put it
                            picturesInSlots = picMap - action.url + (picMap.justAfter(action.index) to action.url)
                        ).asResult()

                }

            }
        }
    }
}


