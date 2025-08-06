package studio.lunabee.microgallery.android.repository.impl

import kotlinx.coroutines.flow.Flow
import studio.lunabee.microgallery.android.data.MicroPicture
import studio.lunabee.microgallery.android.domain.photoviewer.PhotoViewerRepository
import studio.lunabee.microgallery.android.repository.datasource.local.PictureLocal

class PhotoViewerRepositoryImpl(
    private val pictureLocal: PictureLocal,
) : PhotoViewerRepository {

    override fun getPictureById(id: Long): Flow<MicroPicture> {
        return pictureLocal.getPictureById(id)
    }

    override suspend fun getPictureBefore(order: Float): MicroPicture {
        return pictureLocal.getFirstPictureBefore(order)
    }

    override suspend fun getPictureAfter(order: Float): MicroPicture {
        return pictureLocal.getFirstPictureAfter(order)
    }

    override suspend fun getOrderById(id: Long): Float {
        return pictureLocal.getOrderById(id)
    }
}
