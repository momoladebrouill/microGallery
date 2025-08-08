package studio.lunabee.microgallery.android.repository.impl

import kotlinx.coroutines.flow.Flow
import studio.lunabee.microgallery.android.data.MMonth
import studio.lunabee.microgallery.android.data.MYear
import studio.lunabee.microgallery.android.data.MicroPicture
import studio.lunabee.microgallery.android.domain.lastMonth.LastMonthRepository
import studio.lunabee.microgallery.android.repository.datasource.local.PictureLocal

class LastMonthRepositoryImpl(
    private val pictureLocal: PictureLocal,
) : LastMonthRepository {
    override fun getLastMonthPictures(year: MYear, month: MMonth): Flow<List<MicroPicture>> {
        return pictureLocal.getPicturesInMonth(year = year, month = month)
    }
}
