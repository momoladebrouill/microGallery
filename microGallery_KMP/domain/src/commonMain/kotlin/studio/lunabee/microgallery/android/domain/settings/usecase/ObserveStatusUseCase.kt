package studio.lunabee.microgallery.android.domain.settings.usecase

import kotlinx.coroutines.flow.Flow
import studio.lunabee.microgallery.android.data.RemoteStatus
import studio.lunabee.microgallery.android.domain.settings.SettingsRepository

class ObserveStatusUseCase(
    val settingsRepository: SettingsRepository,
) {
    operator fun invoke(): Flow<RemoteStatus> {
        return settingsRepository.getStatus()
    }
}
