package studio.lunabee.microgallery.android.repository.impl

import kotlinx.coroutines.flow.Flow
import studio.lunabee.amicrogallery.picture.PictureLocal
import studio.lunabee.microgallery.android.data.Picture
import studio.lunabee.microgallery.android.domain.photoviewer.PhotoViewerRepository

class PhotoViewerRepositoryImpl(
    private val pictureLocal: PictureLocal,
) : PhotoViewerRepository {

    override fun getPictureById(id: Long): Flow<Picture> {
        return pictureLocal.getPictureById(id)
    }
}
