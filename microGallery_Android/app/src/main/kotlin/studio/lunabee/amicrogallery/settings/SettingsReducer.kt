package studio.lunabee.amicrogallery.settings

import coil3.imageLoader
import kotlinx.coroutines.CoroutineScope
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

    override suspend fun reduce(
        actualState: SettingsUiState,
        action: SettingsAction,
        performNavigation: (SettingsNavScope.() -> Unit) -> Unit,
    ): ReduceResult<SettingsUiState> {
        return when (action) {
            is SettingsAction.JumpBack -> actualState withSideEffect {
                settingsRepository.setSettingsData(actualState.data)
                performNavigation {
                    jumpBack()
                }
            }
            SettingsAction.JumpUntimed -> actualState withSideEffect {
                settingsRepository.setSettingsData(actualState.data)
                performNavigation {
                    jumpUntimed()
                }
            }

            is SettingsAction.ToggleIpv6 -> actualState.copy(
                data = actualState.data.copy(
                    useIpv6 = !(actualState.data.useIpv6),
                ),
            ).asResult()

            is SettingsAction.ToggleViewInHD -> actualState.copy(
                data = actualState.data.copy(
                    viewInHD = !actualState.data.viewInHD,
                ),
            ).asResult()

            is SettingsAction.Clear -> actualState withSideEffect {
                val imageLoader = action.context.imageLoader
                imageLoader.memoryCache?.clear()
                // settingsRepository.clearDB()
            }

            is SettingsAction.GotRemoteStatus -> actualState.copy(remoteStatus = action.status).asResult()

            is SettingsAction.SetIpv4 -> actualState.copy(data = actualState.data.copy(ipv4 = action.ipv4)).asResult()
            is SettingsAction.SetIpv6 -> actualState.copy(data = actualState.data.copy(ipv6 = action.ipv6)).asResult()
            is SettingsAction.GotData ->
                actualState.copy(data = action.data).asResult()
        }
    }
}
