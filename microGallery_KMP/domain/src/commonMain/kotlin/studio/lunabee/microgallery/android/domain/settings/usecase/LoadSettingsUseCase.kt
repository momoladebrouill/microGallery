package studio.lunabee.microgallery.android.domain.settings.usecase

import com.lunabee.lbcore.model.LBResult
import studio.lunabee.amicrogallery.android.error.CoreError
import studio.lunabee.microgallery.android.domain.settings.SettingsRepository

class LoadSettingsUseCase(
    val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(): LBResult<Unit> = CoreError.Companion.runCatching {
        return LBResult.Success(settingsRepository.fetchSettingsData())
    }
}
