package studio.lunabee.amicrogallery.settings

import android.content.Context
import studio.lunabee.microgallery.android.data.RemoteStatus
import studio.lunabee.microgallery.android.data.SettingsData

sealed interface SettingsAction {

    sealed interface LoadingAction : SettingsAction
    sealed interface HasDataAction : SettingsAction

    data class GotData(val data: SettingsData) : LoadingAction

    object JumpBack : LoadingAction, HasDataAction

    data class Clear(
        val context: Context,
    ) : HasDataAction
    data object ToggleIpv6: HasDataAction
    data object ToggleViewInHD : HasDataAction
    data class SetIpv6(
       val ipv6: String
    ) : HasDataAction

    data class SetIpv4(
        val ipv4 : String
    ) : HasDataAction

    object GetRemoteStatus : LoadingAction, HasDataAction
    object GetSettingsData : LoadingAction
    data class GotRemoteStatus(
        val status: RemoteStatus,
    ) : SettingsAction, LoadingAction, HasDataAction
}
