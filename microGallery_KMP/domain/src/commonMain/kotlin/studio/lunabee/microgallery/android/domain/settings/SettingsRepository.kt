package studio.lunabee.microgallery.android.domain.settings

import studio.lunabee.microgallery.android.data.RemoteStatus
import studio.lunabee.microgallery.android.data.SettingsData

interface SettingsRepository {
    suspend fun getSettingsData(): SettingsData
    suspend fun clearDB()

    suspend fun getStatus(): RemoteStatus

    suspend fun setSettingsData(settingsUiData: SettingsData)
}
