package studio.lunabee.amicrogallery.settings

import studio.lunabee.compose.presenter.PresenterUiState
import studio.lunabee.microgallery.android.data.RemoteStatus
import studio.lunabee.microgallery.android.data.SettingsData

data class SettingsUiState(
    val data: SettingsData?,
    val remoteStatus: RemoteStatus?,
) : PresenterUiState
