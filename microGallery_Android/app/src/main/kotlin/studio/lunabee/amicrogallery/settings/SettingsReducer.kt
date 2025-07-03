package studio.lunabee.amicrogallery.settings

import kotlinx.coroutines.CoroutineScope
import studio.lunabee.compose.presenter.LBSingleReducer
import studio.lunabee.compose.presenter.ReduceResult
import studio.lunabee.compose.presenter.withSideEffect

class SettingsReducer(
    override val coroutineScope: CoroutineScope,
    override val emitUserAction: (SettingsAction) -> Unit,
) : LBSingleReducer<SettingsUiState, SettingsNavScope, SettingsAction>() {

    override suspend fun reduce(
        actualState: SettingsUiState,
        action: SettingsAction,
        performNavigation: (SettingsNavScope.() -> Unit) -> Unit,
    ): ReduceResult<SettingsUiState> {
        return when (action) {
            is SettingsAction.JumpBack -> actualState withSideEffect {
                performNavigation {
                    jumpBack()
                }
            }
        }
    }
}
