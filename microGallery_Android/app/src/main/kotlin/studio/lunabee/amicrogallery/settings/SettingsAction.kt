package studio.lunabee.amicrogallery.settings

import studio.lunabee.microgallery.android.data.SettingsData

sealed interface SettingsAction {
    object JumpBack : SettingsAction

    data class GotData( val data : SettingsData) : SettingsAction
}
