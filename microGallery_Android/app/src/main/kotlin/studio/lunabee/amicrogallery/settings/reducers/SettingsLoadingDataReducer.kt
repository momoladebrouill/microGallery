package studio.lunabee.amicrogallery.settings.reducers

import kotlinx.coroutines.CoroutineScope
import studio.lunabee.amicrogallery.settings.SettingsAction
import studio.lunabee.amicrogallery.settings.SettingsAction.Clear
import studio.lunabee.amicrogallery.settings.SettingsAction.SetIpv4
import studio.lunabee.amicrogallery.settings.SettingsAction.SetIpv6
import studio.lunabee.amicrogallery.settings.SettingsNavScope
import studio.lunabee.amicrogallery.settings.SettingsUiState
import studio.lunabee.amicrogallery.settings.SettingsUiState.HasData
import studio.lunabee.compose.presenter.LBReducer
import studio.lunabee.compose.presenter.ReduceResult
import studio.lunabee.compose.presenter.asResult
import studio.lunabee.compose.presenter.withSideEffect
import studio.lunabee.microgallery.android.domain.settings.SettingsRepository

class SettingsLoadingDataReducer(
    override val coroutineScope: CoroutineScope,
    override val emitUserAction: (SettingsAction) -> Unit,
    val settingsRepository: SettingsRepository
) : LBReducer<SettingsUiState.LoadingData,SettingsUiState, SettingsNavScope, SettingsAction, SettingsAction.LoadingAction>() {

    override suspend fun reduce(actualState: SettingsUiState.LoadingData,
        action: SettingsAction.LoadingAction,
        performNavigation: (SettingsNavScope.() -> Unit) -> Unit): ReduceResult<SettingsUiState> {
        return when (action) {

            is SettingsAction.GotData -> {
                println("got data ${action.data}")
                HasData(
                data = action.data,
                remoteStatus = actualState.remoteStatus,
                toggleIpV6 = { emitUserAction(SettingsAction.ToggleIpv6) },
                jumpBack = {emitUserAction(SettingsAction.JumpBack)},
                clearCache = {context -> emitUserAction(Clear(context))},
                getRemoteStatus = {emitUserAction(SettingsAction.GetRemoteStatus)},
                setIpv4 = {emitUserAction(SetIpv4(it))},
                setIpv6 = {emitUserAction(SetIpv6(it))},
                toggleViewInHD = {emitUserAction(SettingsAction.ToggleViewInHD)}
            ).asResult()}

            SettingsAction.JumpBack -> actualState withSideEffect {
                performNavigation { jumpBack() }
            }

            is SettingsAction.GotRemoteStatus -> {
                actualState.copy(remoteStatus =  action.status).asResult()
            }

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

    override fun filterAction(action: SettingsAction): Boolean {
        return action is SettingsAction.LoadingAction
    }

    override fun filterUiState(actualState: SettingsUiState): Boolean {
        return actualState is SettingsUiState.LoadingData
    }
}