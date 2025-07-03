package studio.lunabee.microgallery.android.domain.settings

import studio.lunabee.microgallery.android.data.RemoteStatus
import studio.lunabee.microgallery.android.data.SettingsData

interface SettingsRepository {

    val settingsData : SettingsData
    suspend fun fetchSettingsData()
    suspend fun getSettingsDataFromDB(): SettingsData
    suspend fun clearDB()

    suspend fun getStatus(): RemoteStatus

    suspend fun setSettingsData(settingsUiData: SettingsData)
}
