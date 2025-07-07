package studio.lunabee.microgallery.android.domain.calendar.usecase

import com.lunabee.lbcore.model.LBResult
import studio.lunabee.amicrogallery.android.error.CoreError
import studio.lunabee.microgallery.android.data.YearPreview
import studio.lunabee.microgallery.android.domain.calendar.CalendarRepository

class LoadTreeUseCase(
    val calendarRepository: CalendarRepository,
) {
    suspend operator fun invoke(): LBResult<Pair<List<YearPreview>, MutableMap<String, List<String>>>> = CoreError.Companion.runCatching {
        val years: List<YearPreview> = calendarRepository.getYearPreviews()
        val monthsOfYears = mutableMapOf<String, List<String>>()
        for (year in years.map(YearPreview::year)) {
            val monthsOfYear = calendarRepository.getMonthsInYear(year)
            monthsOfYears[year] = monthsOfYear
        }
        return LBResult.Success(Pair(years, monthsOfYears))
    }
}
