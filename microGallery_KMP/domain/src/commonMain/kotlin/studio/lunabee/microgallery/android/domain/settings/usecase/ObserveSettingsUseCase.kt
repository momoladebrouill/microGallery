package studio.lunabee.microgallery.android.domain.settings.usecase

import kotlinx.coroutines.flow.Flow
import studio.lunabee.microgallery.android.data.SettingsData
import studio.lunabee.microgallery.android.domain.settings.SettingsRepository

class ObserveSettingsUseCase(
    val settingsRepository: SettingsRepository
) {
    operator fun invoke(): Flow<SettingsData>  {
        return settingsRepository.getSettingsDataFromDB()
    }
}
