package studio.lunabee.amicrogallery.settings

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import studio.lunabee.amicrogallery.settings.reducers.SettingsHasDataReducer
import studio.lunabee.compose.presenter.LBPresenter
import studio.lunabee.compose.presenter.LBSimpleReducer
import studio.lunabee.microgallery.android.data.RemoteStatus
import studio.lunabee.microgallery.android.data.SettingsData
import studio.lunabee.microgallery.android.domain.settings.SettingsRepository

class SettingsPresenter(
    val settingsRepository: SettingsRepository,
) : LBPresenter<SettingsUiState, SettingsNavScope, SettingsAction>() {

    override val flows: List<Flow<SettingsAction>> = emptyList()

    override fun getInitialState(): SettingsUiState = SettingsUiState.HasData(
        data = SettingsData(ipv4 = "",
            ipv6 = "TODO()",
            useIpv6 = true,
            viewInHD = true),
        remoteStatus = RemoteStatus(isPlugged = TODO(),
            temperature = TODO(),
            quantityHighRes = TODO(),
            quantityLowRes = TODO()),
        toggleIpV6 = { } ,
        jumpBack = { } ,
        clearCache = { } ,
        getRemoteStatus = { } ,
        setIpv4 = { } ,
        setIpv6 = { } ,
        toggleViewInHD = { } 
    )
    override fun getReducerByState(actualState: SettingsUiState): LBSimpleReducer<SettingsUiState, SettingsNavScope, SettingsAction> {
        return SettingsHasDataReducer(
                viewModelScope,
                ::emitUserAction,
                settingsRepository = settingsRepository
            )
    }


    override val content: @Composable ((SettingsUiState) -> Unit) = { SettingsScreen(it as SettingsUiState.HasData) }
}
