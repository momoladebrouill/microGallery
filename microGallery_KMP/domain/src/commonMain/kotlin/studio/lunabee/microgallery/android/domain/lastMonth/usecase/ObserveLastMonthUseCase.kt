package studio.lunabee.microgallery.android.domain.lastMonth.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import studio.lunabee.microgallery.android.data.MicroPicture
import studio.lunabee.microgallery.android.domain.lastMonth.LastMonthRepository

class ObserveLastMonthUseCase(
    val lastMonthRepository: LastMonthRepository
) {

    operator fun invoke() : Flow<List<MicroPicture>> {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

        val photos: Flow<List<MicroPicture>> = lastMonthRepository.getLastMonthPictures(
            year = now.year.toString(),
            month = "%02d".format(now.monthNumber-1)
        )
        return photos
    }
}