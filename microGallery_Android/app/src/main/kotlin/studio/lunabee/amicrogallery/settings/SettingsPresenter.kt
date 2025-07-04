package studio.lunabee.amicrogallery.settings

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import studio.lunabee.amicrogallery.settings.reducers.SettingsHasDataReducer
import studio.lunabee.amicrogallery.settings.reducers.SettingsLoadingDataReducer
import studio.lunabee.compose.presenter.LBPresenter
import studio.lunabee.compose.presenter.LBSimpleReducer
import studio.lunabee.microgallery.android.domain.settings.SettingsRepository

class SettingsPresenter(
    val settingsRepository: SettingsRepository,
) : LBPresenter<SettingsUiState, SettingsNavScope, SettingsAction>() {
    override val flows: List<Flow<SettingsAction>> = emptyList()

    override fun getInitialState(): SettingsUiState = SettingsUiState.LoadingData(null)
    override fun getReducerByState(actualState: SettingsUiState): LBSimpleReducer<SettingsUiState, SettingsNavScope, SettingsAction> {
        return when(actualState){
            is SettingsUiState.HasData -> SettingsHasDataReducer(
                viewModelScope,
                ::emitUserAction,
                settingsRepository = settingsRepository
            )
            is SettingsUiState.LoadingData -> SettingsLoadingDataReducer(
                coroutineScope = viewModelScope,
                emitUserAction = ::emitUserAction,
                settingsRepository = settingsRepository
            )
        }
    }

    init {
        emitUserAction(SettingsAction.GetSettingsData)
        emitUserAction(SettingsAction.GetRemoteStatus)
    }

    override val content: @Composable ((SettingsUiState) -> Unit) = { SettingsScreen(it) }
}
