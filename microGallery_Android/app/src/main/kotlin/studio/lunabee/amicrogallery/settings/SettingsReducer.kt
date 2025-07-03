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
) : LBSingleReducer<SettingsUiState, SettingsNavScope, SettingsAction>() {

    suspend fun reduceLoadingData(
        actualState: SettingsUiState.HasData,
        action: SettingsAction,
        performNavigation: (SettingsNavScope.() -> Unit) -> Unit,
    ): ReduceResult<SettingsUiState> {
        return when(action) {
            SettingsAction.GotData -> SettingsUiState.HasData(
                data = action.data,
                remoteStatus = actualState.remote
            ).asResult()
        }
    }

    override suspend fun reduce(
        actualState: SettingsUiState.HasData,
        action: SettingsAction,
        performNavigation: (SettingsNavScope.() -> Unit) -> Unit,
    ): ReduceResult<SettingsUiState> {
        return when (action) {
            is
            is SettingsAction.JumpBack -> actualState withSideEffect {
                if (actualState.data == null) {
                    error("Trying to save a null data")
                } else {
                    settingsRepository.setSettingsData(actualState.data)
                }
                performNavigation {
                    jumpBack()
                }
            }

            is SettingsAction.ToggleIpv6 -> actualState.copy(
                data = actualState.data?.copy(
                    useIpv6 = !(actualState.data.useIpv6)
                )
            ).asResult()

            is SettingsAction.Clear -> actualState withSideEffect {
                val imageLoader = action.context.imageLoader
                imageLoader.memoryCache?.clear()
                settingsRepository.clearDB()
            }

            is SettingsAction.GotRemoteStatus -> actualState.copy(remoteStatus = action.status).asResult()
            SettingsAction.GetRemoteStatus -> actualState withSideEffect {
                val remoteStatus = settingsRepository.getStatus()
                emitUserAction(SettingsAction.GotRemoteStatus(remoteStatus))
            }

            SettingsAction.GetSettingsData -> actualState withSideEffect {
                val data = settingsRepository.getSettingsDataFromDB()
                emitUserAction(SettingsAction.GotData(data = data))
            }
        }
    }
}
