package studio.lunabee.microgallery.android.domain.calendar.usecase

import kotlinx.coroutines.flow.Flow
import studio.lunabee.microgallery.android.data.YearPreview
import studio.lunabee.microgallery.android.domain.calendar.CalendarRepository

class ObserveYearPreviewsUseCase(
    val calendarRepository: CalendarRepository
) {
    operator fun invoke() : Flow<List<YearPreview>> {
        return calendarRepository.getYearPreviews()
    }
}