package studio.lunabee.amicrogallery.settings

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import studio.lunabee.compose.presenter.LBSinglePresenter
import studio.lunabee.compose.presenter.LBSingleReducer
import studio.lunabee.microgallery.android.data.SettingsData
import studio.lunabee.microgallery.android.domain.settings.SettingsRepository
import studio.lunabee.microgallery.android.domain.settings.usecase.ObserveSettingsUseCase
import studio.lunabee.microgallery.android.domain.settings.usecase.ObserveStatusUseCase

class SettingsPresenter(
    val settingsRepository: SettingsRepository,
    val observeSettingsUseCase: ObserveSettingsUseCase,
    val observeStatusUseCase: ObserveStatusUseCase,
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
        getRemoteStatus = { },
        setIpv4 = { emitUserAction(SettingsAction.SetIpv4(it)) },
        setIpv6 = { emitUserAction(SettingsAction.SetIpv6(it)) },
        toggleViewInHD = { emitUserAction(SettingsAction.ToggleViewInHD) },
        jumpUntimed = { emitUserAction(SettingsAction.JumpUntimed) },
    )

    override fun initReducer(): LBSingleReducer<SettingsUiState, SettingsNavScope, SettingsAction> {
        return SettingsReducer(
            coroutineScope = viewModelScope,
            emitUserAction = ::emitUserAction,
            settingsRepository = settingsRepository,
        )
    }

    override val content: @Composable ((SettingsUiState) -> Unit) = { SettingsScreen(it) }
}
