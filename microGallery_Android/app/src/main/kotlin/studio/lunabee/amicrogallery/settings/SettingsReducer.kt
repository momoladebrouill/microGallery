package studio.lunabee.amicrogallery.settings

import coil3.imageLoader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import studio.lunabee.compose.presenter.LBSingleReducer
import studio.lunabee.compose.presenter.ReduceResult
import studio.lunabee.compose.presenter.asResult
import studio.lunabee.compose.presenter.withSideEffect
import studio.lunabee.microgallery.android.domain.settings.SettingsRepository

class SettingsReducer(
    override val coroutineScope: CoroutineScope,
    override val emitUserAction: (SettingsAction) -> Unit,
    val settingsRepository: SettingsRepository,
) : LBSingleReducer<SettingsUiState, SettingsNavScope, SettingsAction> () {

    override suspend fun reduce(
        actualState: SettingsUiState,
        action: SettingsAction,
        performNavigation: (SettingsNavScope.() -> Unit) -> Unit,
    ): ReduceResult<SettingsUiState> {
        return when (action) {
            is SettingsAction.GotData -> actualState.copy(data = action.data).asResult()
            is SettingsAction.JumpBack -> actualState withSideEffect {
                coroutineScope.launch {
                    if (actualState.data == null) {
                        error("Trying to save a null data")
                    } else {
                        settingsRepository.setSettingsData(actualState.data)
                    }
                }
                performNavigation {
                    jumpBack()
                }
            }
            is SettingsAction.SetParameters -> actualState.copy(data = action.data).asResult()
            is SettingsAction.Clear -> actualState withSideEffect {
                coroutineScope.launch {
                    val imageLoader = action.context.imageLoader
                    imageLoader.memoryCache?.clear()
                    settingsRepository.clearDB()
                }
            }

            is SettingsAction.GotRemoteStatus -> actualState.copy(remoteStatus = action.status).asResult()
            SettingsAction.GetRemoteStatus -> actualState.withSideEffect {
                coroutineScope.launch {
                    val remoteStatus = settingsRepository.getStatus()
                    emitUserAction(SettingsAction.GotRemoteStatus(remoteStatus))
                }
            }
        }
    }
}
