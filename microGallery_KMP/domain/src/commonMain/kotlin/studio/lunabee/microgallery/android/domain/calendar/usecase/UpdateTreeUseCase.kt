package studio.lunabee.microgallery.android.domain.calendar.usecase

import com.lunabee.lbcore.model.LBResult
import studio.lunabee.amicrogallery.android.error.CoreError
import studio.lunabee.microgallery.android.domain.Node
import studio.lunabee.microgallery.android.domain.calendar.CalendarRepository

class UpdateTreeUseCase(
    val calendarRepository: CalendarRepository,
) {
    suspend operator fun invoke(): LBResult<Node> = CoreError.runCatching {
        return LBResult.Success(calendarRepository.getRootNode())
    }
}
