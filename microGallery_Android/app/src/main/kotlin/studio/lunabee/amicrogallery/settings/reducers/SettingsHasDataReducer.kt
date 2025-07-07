package studio.lunabee.amicrogallery.settings.reducers

import coil3.imageLoader
import kotlinx.coroutines.CoroutineScope
import studio.lunabee.amicrogallery.settings.SettingsAction

import studio.lunabee.amicrogallery.settings.SettingsNavScope
import studio.lunabee.amicrogallery.settings.SettingsUiState
import studio.lunabee.compose.presenter.LBReducer
import studio.lunabee.compose.presenter.ReduceResult
import studio.lunabee.compose.presenter.asResult
import studio.lunabee.compose.presenter.withSideEffect
import studio.lunabee.microgallery.android.domain.settings.SettingsRepository

class SettingsHasDataReducer(
    override val coroutineScope: CoroutineScope,
    override val emitUserAction: (SettingsAction) -> Unit,
    val settingsRepository: SettingsRepository,
) : LBReducer<SettingsUiState.HasData, SettingsUiState, SettingsNavScope, SettingsAction, SettingsAction.HasDataAction>() {

    override suspend fun reduce(actualState: SettingsUiState.HasData,
        action: SettingsAction.HasDataAction,
        performNavigation: (SettingsNavScope.() -> Unit) -> Unit): ReduceResult<SettingsUiState> {
        return when (action) {
            is SettingsAction.JumpBack -> actualState withSideEffect {
                settingsRepository.setSettingsData(actualState.data)
                performNavigation {
                    jumpBack()
                }
            }

            is SettingsAction.ToggleIpv6 -> actualState.copy(
                data = actualState.data.copy(
                    useIpv6 = !(actualState.data.useIpv6)
                )
            ).asResult()

            is SettingsAction.Clear -> actualState withSideEffect {
                val imageLoader = action.context.imageLoader
                imageLoader.memoryCache?.clear()
                //settingsRepository.clearDB()
            }

            is SettingsAction.GotRemoteStatus -> actualState.copy(remoteStatus = action.status).asResult()

            is SettingsAction.SetIpv4 -> actualState.copy(data = actualState.data.copy(ipv4 = action.ipv4)).asResult()
            is SettingsAction.SetIpv6 -> actualState.copy(data = actualState.data.copy(ipv6 = action.ipv6)).asResult()
            SettingsAction.ToggleViewInHD -> actualState.copy(data = actualState.data.copy(viewInHD = !actualState.data.viewInHD)).asResult()

        }
    }

    override fun filterAction(action: SettingsAction): Boolean {
        return action is SettingsAction.HasDataAction
    }

    override fun filterUiState(actualState: SettingsUiState): Boolean {
        return actualState is SettingsUiState.HasData
    }
}
