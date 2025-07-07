package studio.lunabee.microgallery.android.domain.photoviewer.usecase

import kotlinx.coroutines.flow.Flow
import studio.lunabee.microgallery.android.data.MicroPicture
import studio.lunabee.microgallery.android.domain.photoviewer.PhotoViewerRepository

class ObservePictureByIdUseCase(
    val photoViewerRepository: PhotoViewerRepository
) {

    operator fun invoke(id : Long) : Flow<MicroPicture> {
        return photoViewerRepository.getPictureById(id)
    }
}