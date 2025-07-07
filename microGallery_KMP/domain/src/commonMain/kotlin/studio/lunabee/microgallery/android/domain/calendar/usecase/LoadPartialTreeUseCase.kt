package studio.lunabee.microgallery.android.domain.calendar.usecase

import com.lunabee.lbcore.model.LBResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import studio.lunabee.amicrogallery.android.error.CoreError
import studio.lunabee.microgallery.android.data.MMonth
import studio.lunabee.microgallery.android.data.MYear
import studio.lunabee.microgallery.android.data.YearPreview
import studio.lunabee.microgallery.android.domain.calendar.CalendarRepository
import kotlin.collections.forEach

class LoadPartialTreeUseCase( // load part of the tree
    val observeYearPreviewsUseCase: ObserveYearPreviewsUseCase
) {
    val yearPreviewsUseCase = observeYearPreviewsUseCase()
    val calendarRepository = observeYearPreviewsUseCase.calendarRepository

    operator fun invoke(): Flow<Map<MYear, List<MMonth>>> =
        yearPreviewsUseCase.map { yearPreviews ->
            val monthsOfYears = mutableMapOf<MYear, List<MMonth>>()
            yearPreviews.forEach {
                monthsOfYears[it.year] = calendarRepository.getMonthsInYear(it.year).first()
            }
            monthsOfYears.toMap()
        }
}

