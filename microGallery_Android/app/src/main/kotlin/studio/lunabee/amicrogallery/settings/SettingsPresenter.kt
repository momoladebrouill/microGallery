package studio.lunabee.amicrogallery.settings

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import studio.lunabee.compose.presenter.LBSinglePresenter
import studio.lunabee.compose.presenter.LBSingleReducer
import studio.lunabee.microgallery.android.domain.settings.SettingsRepository

class SettingsPresenter(
    val settingsRepository: SettingsRepository
) : LBSinglePresenter<SettingsUiState, SettingsNavScope, SettingsAction>() {
    override val flows: List<Flow<SettingsAction>> = emptyList()

    override fun getInitialState(): SettingsUiState = SettingsUiState(data = null)

    init{
        viewModelScope.launch {
            val data = settingsRepository.getSettingsData()
            emitUserAction(SettingsAction.GotData(data = data))
        }
    }

    override fun initReducer(): LBSingleReducer<SettingsUiState, SettingsNavScope, SettingsAction> {
        return SettingsReducer(viewModelScope, ::emitUserAction)
    }

    override val content: @Composable ((SettingsUiState) -> Unit) = { SettingsScreen(it,::emitUserAction) }
}
