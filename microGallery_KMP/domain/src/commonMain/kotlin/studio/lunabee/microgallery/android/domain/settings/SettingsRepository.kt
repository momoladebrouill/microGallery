package studio.lunabee.microgallery.android.domain.settings

import kotlinx.coroutines.flow.Flow
import studio.lunabee.microgallery.android.data.RemoteStatus
import studio.lunabee.microgallery.android.data.SettingsData

interface SettingsRepository {
    fun getSettingsDataFromDB(): Flow<SettingsData>
    fun getStatus(): Flow<RemoteStatus>
    suspend fun setSettingsData(settingsUiData: SettingsData)
}
