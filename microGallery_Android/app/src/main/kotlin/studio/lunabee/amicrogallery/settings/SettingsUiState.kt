package studio.lunabee.amicrogallery.settings

import studio.lunabee.compose.presenter.PresenterUiState
import studio.lunabee.microgallery.android.data.RemoteStatus
import studio.lunabee.microgallery.android.data.SettingsData

sealed interface SettingsUiState : PresenterUiState {

    object LoadingData : SettingsUiState
    data class HasData(
        val data: SettingsData,
        val remoteStatus: RemoteStatus?,
        val toggleIpV6: () -> Unit,
        val jumpBack: () -> Unit,
        val clearCache: () -> Unit,
        val getRemoteStatus: () -> Unit,
    ) : SettingsUiState
}
