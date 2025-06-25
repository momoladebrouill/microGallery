package studio.lunabee.microgallery.android.domain.loading.usecase

import com.lunabee.lbcore.model.LBResult
import studio.lunabee.amicrogallery.android.error.CoreError
import studio.lunabee.microgallery.android.domain.loading.LoadingRepository

class UpdateTreeUseCase(
    val loadingRepository: LoadingRepository,
) {
    suspend operator fun invoke(): LBResult<Unit> = CoreError.Companion.runCatching {
        return LBResult.Success(loadingRepository.fetchRootNode())
    }
}