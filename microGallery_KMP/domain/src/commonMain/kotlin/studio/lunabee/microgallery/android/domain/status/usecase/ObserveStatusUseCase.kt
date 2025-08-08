package studio.lunabee.microgallery.android.domain.status.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import studio.lunabee.microgallery.android.data.RemoteStatus
import studio.lunabee.microgallery.android.domain.status.StatusRepository

class ObserveStatusUseCase(
    val statusRepository: StatusRepository,
) {
    operator fun invoke(): Flow<RemoteStatus> {
        return statusRepository.getStatus().filterNotNull()
    }
}
