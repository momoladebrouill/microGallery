package studio.lunabee.amicrogallery.photoviewer

import studio.lunabee.microgallery.android.data.MicroPicture
import studio.lunabee.microgallery.android.data.Picture

sealed interface PhotoViewerAction {
    data class FoundPicture(
        val picture: MicroPicture,
    ) : PhotoViewerAction
}
