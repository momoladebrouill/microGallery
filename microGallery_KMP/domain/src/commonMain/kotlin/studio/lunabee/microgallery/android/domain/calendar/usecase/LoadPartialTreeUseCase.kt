package studio.lunabee.microgallery.android.domain.calendar.usecase

import com.lunabee.lbcore.model.LBResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import studio.lunabee.amicrogallery.android.error.CoreError
import studio.lunabee.microgallery.android.data.MMonth
import studio.lunabee.microgallery.android.data.MYear
import studio.lunabee.microgallery.android.data.YearPreview
import studio.lunabee.microgallery.android.domain.calendar.CalendarRepository

class LoadPartialTreeUseCase( // load part of the tree
    val calendarRepository: CalendarRepository,
    val observeYearPreviewsUseCase: ObserveYearPreviewsUseCase
) {
    val yearPreviewsUseCase = observeYearPreviewsUseCase()

    operator fun invoke(): Flow<Map<MYear, Flow<List<MMonth>>>> =
        yearPreviewsUseCase.map { yearPreviews ->
            val monthsOfYears = mutableMapOf<MYear, Flow<List<MMonth>>>()
            yearPreviews.forEach {
                monthsOfYears[it.year] = calendarRepository.getMonthsInYear(it.year)
            }
            monthsOfYears.toMap()
        }
}

