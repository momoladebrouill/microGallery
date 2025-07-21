package studio.lunabee.microgallery.android.domain.reorder.usecase

import kotlinx.coroutines.flow.first
import studio.lunabee.microgallery.android.data.MYear
import studio.lunabee.microgallery.android.data.MicroPicture
import studio.lunabee.microgallery.android.domain.loading.usecase.ListYearsFlowUseCase
import studio.lunabee.microgallery.android.domain.reorder.ReorderRepository
import studio.lunabee.microgallery.android.domain.utils.cycle
import studio.lunabee.microgallery.android.domain.utils.derange

class GetPicturesShuffledUseCase(
    val reorderRepository: ReorderRepository,
    val listYearsFlowUseCase: ListYearsFlowUseCase,
) {
    suspend operator fun invoke(qty: Int): Array<MicroPicture> {
        val years: Iterator<MYear> = derange(listYearsFlowUseCase().first().toTypedArray()).cycle()
        return Array(qty) { index ->
            reorderRepository.getRandomInYear(years.next())
        }
    }
}
