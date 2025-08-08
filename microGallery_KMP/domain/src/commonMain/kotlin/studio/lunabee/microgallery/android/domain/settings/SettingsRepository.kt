package studio.lunabee.microgallery.android.domain.settings

import kotlinx.coroutines.flow.Flow
import studio.lunabee.microgallery.android.data.RemoteStatus

interface SettingsRepository {
    suspend fun clearPictureDB()
}
