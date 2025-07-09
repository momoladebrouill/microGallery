package studio.lunabee.microgallery.android.domain.settings.usecase

import studio.lunabee.microgallery.android.domain.loading.LoadingRepository
import studio.lunabee.microgallery.android.domain.settings.SettingsRepository

class EmptyPhotoDbUseCase(
    val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke() {
        return settingsRepository.clearPictureDB()
    }
}