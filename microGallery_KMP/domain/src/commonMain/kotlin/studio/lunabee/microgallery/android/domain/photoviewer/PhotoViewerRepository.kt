package studio.lunabee.microgallery.android.domain.photoviewer

import kotlinx.coroutines.flow.Flow
import studio.lunabee.microgallery.android.data.Picture

interface PhotoViewerRepository {
     fun getPictureById(id: Long): Flow<Picture>
}
