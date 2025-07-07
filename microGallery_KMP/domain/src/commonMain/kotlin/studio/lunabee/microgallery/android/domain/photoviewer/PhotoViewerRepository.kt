package studio.lunabee.microgallery.android.domain.photoviewer

import kotlinx.coroutines.flow.Flow
import studio.lunabee.microgallery.android.data.MicroPicture
import studio.lunabee.microgallery.android.data.Picture

interface PhotoViewerRepository {
     fun getPictureById(id: Long): Flow<MicroPicture>
}
