package studio.lunabee.microgallery.android.repository.impl

import studio.lunabee.amicrogallery.picture.PictureLocal
import studio.lunabee.microgallery.android.data.Picture
import studio.lunabee.microgallery.android.domain.untimed.UntimedRepository

class UntimedRepositoryImpl(
    private val pictureLocal: PictureLocal,
) : UntimedRepository {
    override suspend fun getPicturesUntimed(): List<Picture> {
        return pictureLocal.getPicturesUntimed()
    }
}
