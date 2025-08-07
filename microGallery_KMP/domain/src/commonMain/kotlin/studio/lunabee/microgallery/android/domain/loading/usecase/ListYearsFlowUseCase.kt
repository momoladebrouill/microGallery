package studio.lunabee.microgallery.android.domain.loading.usecase

import kotlinx.coroutines.flow.Flow
import studio.lunabee.microgallery.android.data.MYear
import studio.lunabee.microgallery.android.domain.loading.LoadingRepository

class ListYearsFlowUseCase(
    val loadingRepository: LoadingRepository,
) {
    operator fun invoke(): Flow<List<MYear>> {
        return loadingRepository.yearsInDb()
    }
}
