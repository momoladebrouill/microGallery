package studio.lunabee.microgallery.android.domain.photoviewer.usecase

import studio.lunabee.microgallery.android.data.MicroPicture
import studio.lunabee.microgallery.android.domain.photoviewer.PhotoViewerRepository

class GetNeighborsByPictureUseCase(
    val photoViewerRepository: PhotoViewerRepository,
) {

    suspend operator fun invoke(centerPicId: Long): Pair<MicroPicture, MicroPicture> {
        val order: Float = photoViewerRepository.getOrderById(centerPicId)
        val left = photoViewerRepository.getPictureBefore(order)
        val right = photoViewerRepository.getPictureAfter(order)
        return Pair(left, right)
    }
}
