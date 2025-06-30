package studio.lunabee.microgallery.android.repository.impl

import studio.lunabee.amicrogallery.picture.PictureLocal
import studio.lunabee.microgallery.android.data.Picture
import studio.lunabee.microgallery.android.domain.photoviewer.PhotoViewerRepository

class PhotoViewerRepositoryImpl(
    private val pictureLocal: PictureLocal,
) : PhotoViewerRepository {

    override suspend fun getPictureById(id: Long): Picture {
        return pictureLocal.getPictureById(id)
    }
}
