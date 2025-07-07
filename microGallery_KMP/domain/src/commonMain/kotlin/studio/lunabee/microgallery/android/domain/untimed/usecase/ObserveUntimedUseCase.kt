package studio.lunabee.microgallery.android.domain.untimed.usecase

import kotlinx.coroutines.flow.Flow
import studio.lunabee.microgallery.android.data.MicroPicture
import studio.lunabee.microgallery.android.domain.untimed.UntimedRepository

class ObserveUntimedUseCase(
    val untimedRepository: UntimedRepository,
) {

    operator fun invoke(): Flow<List<MicroPicture>> {
        return untimedRepository.getPicturesUntimed()
    }
}
