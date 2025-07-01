package studio.lunabee.microgallery.android.domain.calendar

import studio.lunabee.microgallery.android.data.Picture
import studio.lunabee.microgallery.android.data.YearPreview

interface CalendarRepository {
    suspend fun getYearPreviews(): List<YearPreview>
    suspend fun getMonthsInYear(year: String): List<String>
    suspend fun getPicturesInMonth(year: String, month: String): List<Picture>
}
