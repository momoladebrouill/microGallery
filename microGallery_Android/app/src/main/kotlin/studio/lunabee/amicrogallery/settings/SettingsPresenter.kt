package studio.lunabee.amicrogallery.settings

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import studio.lunabee.amicrogallery.snackbar.SnackBarManager
import studio.lunabee.compose.presenter.LBSinglePresenter
import studio.lunabee.compose.presenter.LBSingleReducer
import studio.lunabee.microgallery.android.data.SettingsData
import studio.lunabee.microgallery.android.domain.settings.SettingsRepository
import studio.lunabee.microgallery.android.domain.settings.usecase.ObserveSettingsUseCase
import studio.lunabee.microgallery.android.domain.status.usecase.ObserveStatusUseCase
import studio.lunabee.microgallery.android.domain.status.usecase.SetStatusUseCase

class SettingsPresenter(
    val settingsRepository: SettingsRepository,
    val observeSettingsUseCase: ObserveSettingsUseCase,
    val observeStatusUseCase: ObserveStatusUseCase,
    val setStatusUseCase: SetStatusUseCase,
    val snackBarManager: SnackBarManager,
) : LBSinglePresenter<SettingsUiState, SettingsNavScope, SettingsAction>() {

    val settingsData = observeSettingsUseCase().map {
        SettingsAction.GotData(it)
    }

    val statusData = observeStatusUseCase().map {
        SettingsAction.GotRemoteStatus(it)
    }

    override val flows: List<Flow<SettingsAction>> = listOf(
        settingsData,
        statusData,
    )

    init {
        viewModelScope.launch {
            setStatusUseCase()
        }
    }

    override fun getInitialState(): SettingsUiState = SettingsUiState(
        data = SettingsData(
            ipv4 = "",
            ipv6 = "",
            useIpv6 = true,
            viewInHD = true,
        ),
        remoteStatus = null,
        toggleIpV6 = { emitUserAction(SettingsAction.ToggleIpv6) },
        jumpBack = { emitUserAction(SettingsAction.JumpBack) },
        clearCache = { emitUserAction(SettingsAction.Clear(it)) },
        getRemoteStatus = { emitUserAction(SettingsAction.GetRemote) },
        setIpv4 = { emitUserAction(SettingsAction.SetIpv4(it)) },
        setIpv6 = { emitUserAction(SettingsAction.SetIpv6(it)) },
        toggleViewInHD = { emitUserAction(SettingsAction.ToggleViewInHD) },
    )

    override fun initReducer(): LBSingleReducer<SettingsUiState, SettingsNavScope, SettingsAction> {
        return SettingsReducer(
            coroutineScope = viewModelScope,
            emitUserAction = ::emitUserAction,
            settingsRepository = settingsRepository,
            setStatusUseCase = setStatusUseCase,
            snackBarManager = snackBarManager,
        )
    }

    override val content: @Composable ((SettingsUiState) -> Unit) = { SettingsScreen(it) }
}
