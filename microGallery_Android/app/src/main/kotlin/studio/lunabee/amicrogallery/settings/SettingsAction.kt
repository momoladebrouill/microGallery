package studio.lunabee.amicrogallery.settings

import android.content.Context
import studio.lunabee.microgallery.android.data.RemoteStatus
import studio.lunabee.microgallery.android.data.SettingsData

sealed interface SettingsAction {
    object JumpBack : SettingsAction

    data class GotData(val data: SettingsData) : SettingsAction

    data class Clear(
        val context: Context,
    ) : SettingsAction
    data class SetParameters(
        val data: SettingsData,
    ) : SettingsAction

    object GetRemoteStatus : SettingsAction
    data class GotRemoteStatus(
        val status: RemoteStatus,
    ) : SettingsAction
}
