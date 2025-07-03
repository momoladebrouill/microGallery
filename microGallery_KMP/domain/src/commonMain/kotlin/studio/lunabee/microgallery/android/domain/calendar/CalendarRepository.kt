package studio.lunabee.microgallery.android.domain.calendar

import studio.lunabee.microgallery.android.data.MMonth
import studio.lunabee.microgallery.android.data.MYear
import studio.lunabee.microgallery.android.data.Picture
import studio.lunabee.microgallery.android.data.YearPreview

interface CalendarRepository {
    suspend fun getYearPreviews(): List<YearPreview>
    suspend fun getMonthsInYear(year: MYear): List<String>
    suspend fun getPicturesInMonth(year: MYear, month: MMonth): List<Picture>
}
