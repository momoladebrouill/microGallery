package studio.lunabee.microgallery.android.domain.settings.usecase

import studio.lunabee.microgallery.android.data.SettingsData
import studio.lunabee.microgallery.android.domain.SettingsDataRepository
import studio.lunabee.microgallery.android.domain.loading.LoadingRepository
import studio.lunabee.microgallery.android.domain.settings.SettingsRepository

class SetSettingsUseCase(
    val settingsDataRepository: SettingsDataRepository
) {
    suspend operator fun invoke(settingsData: SettingsData) {
        return settingsDataRepository.setSettingsData(settingsData)
    }
}