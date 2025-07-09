package studio.lunabee.microgallery.android.domain.settings.usecase

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import studio.lunabee.microgallery.android.data.SettingsData
import studio.lunabee.microgallery.android.domain.SettingsDataRepository
import studio.lunabee.microgallery.android.domain.settings.SettingsRepository

class ObserveSettingsUseCase(
    val settingsDataRepository: SettingsDataRepository,
) {
    operator fun invoke(): Flow<SettingsData> {
        return settingsDataRepository.getSettingsData()
    }
}
