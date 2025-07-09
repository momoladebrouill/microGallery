package studio.lunabee.microgallery.android.domain

import kotlinx.coroutines.flow.Flow
import studio.lunabee.microgallery.android.data.RemoteStatus
import studio.lunabee.microgallery.android.data.SettingsData

interface SettingsDataRepository {
    fun getSettingsData(): Flow<SettingsData>
    suspend fun clearSettingsDB()
    suspend fun setSettingsData(settingsUiData: SettingsData)
}
