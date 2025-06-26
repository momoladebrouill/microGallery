package studio.lunabee.amicrogallery.untimed 
import kotlinx.coroutines.CoroutineScope
import studio.lunabee.compose.presenter.LBSingleReducer
import studio.lunabee.compose.presenter.ReduceResult
import studio.lunabee.compose.presenter.asResult

class UntimedReducer(
    override val coroutineScope: CoroutineScope,
    override val emitUserAction: (UntimedAction) -> Unit,
) : LBSingleReducer<UntimedUiState, UntimedNavScope, UntimedAction>() {
    override suspend fun reduce(
        actualState: UntimedUiState,
        action: UntimedAction,
        performNavigation: (UntimedNavScope.() -> Unit) -> Unit,
    ): ReduceResult<UntimedUiState> {
        return when(action){
            is UntimedAction.GotTheList -> actualState.copy(images = action.images).asResult()
        }
    }
}