package studio.lunabee.amicrogallery.settings

import android.content.Context
import studio.lunabee.microgallery.android.data.RemoteStatus
import studio.lunabee.microgallery.android.data.SettingsData

sealed interface SettingsAction {
    data class GotData(val data: SettingsData) : SettingsAction

    object JumpBack : SettingsAction

    object GetRemote : SettingsAction

    data class Clear(
        val context: Context,
    ) : SettingsAction

    data object ToggleIpv6 : SettingsAction
    data object ToggleViewInHD : SettingsAction
    data class SetIpv6(
        val ipv6: String,
    ) : SettingsAction

    data class SetIpv4(
        val ipv4: String,
    ) : SettingsAction

    data class GotRemoteStatus(
        val status: RemoteStatus,
    ) : SettingsAction
}
