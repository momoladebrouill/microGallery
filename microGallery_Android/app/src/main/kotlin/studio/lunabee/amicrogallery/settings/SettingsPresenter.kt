package studio.lunabee.amicrogallery.settings

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import studio.lunabee.compose.presenter.LBSinglePresenter
import studio.lunabee.compose.presenter.LBSingleReducer

class SettingsPresenter : LBSinglePresenter<SettingsUiState, SettingsNavScope, SettingsAction>() {
    override val flows: List<Flow<SettingsAction>> = emptyList()

    override fun getInitialState(): SettingsUiState = SettingsUiState()

    override fun initReducer(): LBSingleReducer<SettingsUiState, SettingsNavScope, SettingsAction> {
        return SettingsReducer(viewModelScope, ::emitUserAction)
    }

    override val content: @Composable ((SettingsUiState) -> Unit) = { SettingsScreen(it) }
}
