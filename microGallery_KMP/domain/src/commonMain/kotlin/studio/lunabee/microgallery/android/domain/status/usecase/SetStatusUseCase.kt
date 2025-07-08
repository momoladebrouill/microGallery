package studio.lunabee.microgallery.android.domain.status.usecase

import studio.lunabee.microgallery.android.domain.status.StatusRepository

class SetStatusUseCase(
    val statusRepository: StatusRepository,
) {
    suspend operator fun invoke() {
        statusRepository.setStatus(statusRepository.getStatusFromRemote())
    }
}
