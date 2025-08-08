package studio.lunabee.microgallery.android.domain.untimed

import kotlinx.coroutines.flow.Flow
import studio.lunabee.microgallery.android.data.MicroPicture

interface UntimedRepository {
    fun getPicturesUntimed(): Flow<List<MicroPicture>>
}
