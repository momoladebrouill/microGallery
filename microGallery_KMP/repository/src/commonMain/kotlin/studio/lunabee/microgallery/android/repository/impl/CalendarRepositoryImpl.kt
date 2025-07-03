package studio.lunabee.microgallery.android.repository.impl

import studio.lunabee.amicrogallery.picture.PictureLocal
import studio.lunabee.microgallery.android.data.MMonth
import studio.lunabee.microgallery.android.data.MYear
import studio.lunabee.microgallery.android.data.Picture
import studio.lunabee.microgallery.android.data.YearPreview
import studio.lunabee.microgallery.android.domain.calendar.CalendarRepository

class CalendarRepositoryImpl(
    private val pictureLocal: PictureLocal,
) : CalendarRepository {

    override suspend fun getMonthsInYear(year: MYear): List<MMonth> {
        return pictureLocal.getMonthsInYear(year = year)
    }

    override suspend fun getPicturesInMonth(
        year: MYear,
        month: MMonth,
    ): List<Picture> {
        return pictureLocal.getPicturesInMonth(year, month)
    }

    override suspend fun getYearPreviews(): List<YearPreview> {
        return pictureLocal.getYearPreviews()
    }
}
