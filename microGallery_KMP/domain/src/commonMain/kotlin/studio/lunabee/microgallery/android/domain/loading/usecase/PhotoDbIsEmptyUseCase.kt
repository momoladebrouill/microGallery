package studio.lunabee.microgallery.android.domain.loading.usecase

import studio.lunabee.microgallery.android.domain.loading.LoadingRepository

class PhotoDbIsEmptyUseCase(
    val loadingRepository: LoadingRepository,
) {
    suspend operator fun invoke(): Boolean {
        return loadingRepository.isPictureDbEmpty()
    }
}
