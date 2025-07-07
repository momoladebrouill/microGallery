package studio.lunabee.amicrogallery.settings

import android.content.Context
import studio.lunabee.compose.presenter.PresenterUiState
import studio.lunabee.microgallery.android.data.RemoteStatus
import studio.lunabee.microgallery.android.data.SettingsData

sealed interface SettingsUiState : PresenterUiState {

    data class HasData(
        val data: SettingsData,
        val remoteStatus: RemoteStatus?,
        val toggleIpV6: () -> Unit,
        val jumpBack: () -> Unit,
        val clearCache: (Context) -> Unit,
        val getRemoteStatus: () -> Unit,
        val setIpv4 : (String) -> Unit,
        val setIpv6 : (String) -> Unit,
        val toggleViewInHD : () -> Unit
    ) : SettingsUiState
}
