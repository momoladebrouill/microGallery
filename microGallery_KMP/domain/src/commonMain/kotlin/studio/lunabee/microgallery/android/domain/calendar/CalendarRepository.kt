package studio.lunabee.microgallery.android.domain.calendar

import kotlinx.coroutines.flow.Flow
import studio.lunabee.microgallery.android.data.Node
import studio.lunabee.microgallery.android.data.Picture

interface CalendarRepository {
    suspend fun getYears() : Flow<List<Int>>
    suspend fun getMonthsInYear(year : Int) : List<Int>
    suspend fun getPicturesInMonth(year : Int, month : Int) : List<Picture>
}
