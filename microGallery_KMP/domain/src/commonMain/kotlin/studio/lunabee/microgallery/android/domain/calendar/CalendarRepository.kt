package studio.lunabee.microgallery.android.domain.calendar

import studio.lunabee.microgallery.android.data.Picture

interface CalendarRepository {
    suspend fun getYearsAndExample(): List<Pair<String, String>>
    suspend fun getMonthsInYear(year: String): List<String>
    suspend fun getPicturesInMonth(year: String, month: String): List<Picture>
}
