package studio.lunabee.microgallery.android.domain.untimed

import kotlinx.coroutines.flow.Flow
import studio.lunabee.microgallery.android.data.MicroPicture
import studio.lunabee.microgallery.android.data.Picture

interface UntimedRepository {
    fun getPicturesUntimed(): Flow<List<MicroPicture>>
}
