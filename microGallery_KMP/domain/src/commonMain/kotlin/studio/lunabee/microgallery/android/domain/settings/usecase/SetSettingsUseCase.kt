package studio.lunabee.microgallery.android.domain.settings.usecase

import studio.lunabee.microgallery.android.data.SettingsData
import studio.lunabee.microgallery.android.domain.SetClientHttpUrlUseCase
import studio.lunabee.microgallery.android.domain.SettingsDataRepository

class SetSettingsUseCase(
    val settingsDataRepository: SettingsDataRepository,
    val setClientHttpUrlUseCase: SetClientHttpUrlUseCase,
) {
    suspend operator fun invoke(settingsData: SettingsData) {
        setClientHttpUrlUseCase(settingsData)
        return settingsDataRepository.setSettingsData(settingsData)
    }
}
