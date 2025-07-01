package studio.lunabee.microgallery.android.domain.calendar.usecase

import com.lunabee.lbcore.model.LBResult
import kotlinx.coroutines.launch
import studio.lunabee.amicrogallery.android.error.CoreError
import studio.lunabee.microgallery.android.domain.calendar.CalendarRepository
import studio.lunabee.microgallery.android.domain.loading.LoadingRepository

class LoadTreeUseCase(
    val calendarRepository: CalendarRepository,
) {
    suspend operator fun invoke(): LBResult<MutableMap<String, List<String>>> = CoreError.Companion.runCatching {
        val years: List<String> = calendarRepository.getYears()
        val monthsOfYears = mutableMapOf<String, List<String>>()
        for (year in years) {
            val monthsOfYear = calendarRepository.getMonthsInYear(year)
            monthsOfYears[year] = monthsOfYear
        }
        return LBResult.Success(monthsOfYears)
    }

}
