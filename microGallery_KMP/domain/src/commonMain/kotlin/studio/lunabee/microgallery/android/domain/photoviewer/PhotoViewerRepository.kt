package studio.lunabee.microgallery.android.domain.photoviewer

import kotlinx.coroutines.flow.Flow
import studio.lunabee.microgallery.android.data.MicroPicture

interface PhotoViewerRepository {
    fun getPictureById(id: Long): Flow<MicroPicture>
    suspend fun getPictureBefore(order: Float): MicroPicture
    suspend fun getPictureAfter(order: Float): MicroPicture
    suspend fun getOrderById(id: Long): Float
}
