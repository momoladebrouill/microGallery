package studio.lunabee.microgallery.android.repository.impl

import kotlinx.coroutines.flow.Flow
import studio.lunabee.microgallery.android.data.MicroPicture
import studio.lunabee.microgallery.android.domain.untimed.UntimedRepository
import studio.lunabee.microgallery.android.repository.datasource.local.PictureLocal

class UntimedRepositoryImpl(
    private val pictureLocal: PictureLocal,
) : UntimedRepository {
    override fun getPicturesUntimed(): Flow<List<MicroPicture>> {
        return pictureLocal.getPicturesUntimed()
    }
}
