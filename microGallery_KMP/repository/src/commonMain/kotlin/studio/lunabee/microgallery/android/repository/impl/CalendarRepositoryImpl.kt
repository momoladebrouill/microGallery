package studio.lunabee.microgallery.android.repository.impl

import kotlinx.coroutines.flow.Flow
import studio.lunabee.amicrogallery.picture.PictureLocal
import studio.lunabee.microgallery.android.data.MMonth
import studio.lunabee.microgallery.android.data.MYear
import studio.lunabee.microgallery.android.data.Picture
import studio.lunabee.microgallery.android.data.YearPreview
import studio.lunabee.microgallery.android.domain.calendar.CalendarRepository

class CalendarRepositoryImpl(
    private val pictureLocal: PictureLocal,
) : CalendarRepository {

    override fun getMonthsInYear(year: MYear): Flow<List<MMonth>> {
        return pictureLocal.getMonthsInYear(year = year)
    }

    override fun getPicturesInMonth(
        year: MYear,
        month: MMonth,
    ): Flow<List<Picture>> {
        return pictureLocal.getPicturesInMonth(year, month)
    }

    override fun getYearPreviews(): Flow<List<YearPreview>> {
        return pictureLocal.getYearPreviews()
    }
}
