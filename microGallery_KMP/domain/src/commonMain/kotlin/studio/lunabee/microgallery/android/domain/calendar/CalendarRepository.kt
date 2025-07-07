package studio.lunabee.microgallery.android.domain.calendar

import kotlinx.coroutines.flow.Flow
import studio.lunabee.microgallery.android.data.MMonth
import studio.lunabee.microgallery.android.data.MYear
import studio.lunabee.microgallery.android.data.MicroPicture
import studio.lunabee.microgallery.android.data.YearPreview

interface CalendarRepository {
    fun getYearPreviews(): Flow<List<YearPreview>>
    fun getMonthsInYear(year: MYear): Flow<List<String>>
    fun getPicturesInMonth(year: MYear, month: MMonth): Flow<List<MicroPicture>>
}
