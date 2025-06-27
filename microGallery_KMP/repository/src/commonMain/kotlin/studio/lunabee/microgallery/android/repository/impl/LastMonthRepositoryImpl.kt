package studio.lunabee.microgallery.android.repository.impl

import studio.lunabee.amicrogallery.picture.PictureLocal
import studio.lunabee.microgallery.android.data.Picture
import studio.lunabee.microgallery.android.domain.lastMonth.LastMonthRepository

class LastMonthRepositoryImpl(
    private val pictureLocal: PictureLocal,
) : LastMonthRepository {
    override suspend fun getLastMonthPictures(year: String, month: String): List<Picture> {
        return pictureLocal.getPicturesInMonth(year = year, month = month)
    }

}
