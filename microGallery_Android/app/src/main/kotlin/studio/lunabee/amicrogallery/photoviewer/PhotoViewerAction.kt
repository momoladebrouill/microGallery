package studio.lunabee.amicrogallery.photoviewer

import studio.lunabee.microgallery.android.data.MicroPicture

sealed interface PhotoViewerAction {
    data class FoundPicture(
        val picture: MicroPicture,
    ) : PhotoViewerAction
}
