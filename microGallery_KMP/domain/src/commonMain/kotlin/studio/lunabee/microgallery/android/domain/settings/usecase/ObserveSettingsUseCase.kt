package studio.lunabee.microgallery.android.domain.settings.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import studio.lunabee.microgallery.android.data.SettingsData
import studio.lunabee.microgallery.android.domain.SettingsDataRepository

class ObserveSettingsUseCase(
    val settingsDataRepository: SettingsDataRepository,
) {
    operator fun invoke(): Flow<SettingsData> {
        return settingsRepository.getSettingsData().filterNotNull()
    }
}
