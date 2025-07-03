package studio.lunabee.microgallery.android.repository.impl

import studio.lunabee.amicrogallery.picture.PictureLocal
import studio.lunabee.microgallery.android.data.MMonth
import studio.lunabee.microgallery.android.data.MYear
import studio.lunabee.microgallery.android.data.Picture
import studio.lunabee.microgallery.android.domain.lastMonth.LastMonthRepository

class LastMonthRepositoryImpl(
    private val pictureLocal: PictureLocal,
) : LastMonthRepository {
    override suspend fun getLastMonthPictures(year: MYear, month: MMonth): List<Picture> {
        return pictureLocal.getPicturesInMonth(year = year, month = month)
    }
}
