package studio.lunabee.amicrogallery.photoviewer

import android.content.Context
import studio.lunabee.microgallery.android.data.Picture

sealed interface PhotoViewerAction {
    data class FoundPicture(
        val picture: Picture,
    ) : PhotoViewerAction

    data class SharePicture(
        val picture: Picture,
        val context: Context,
    ) : PhotoViewerAction
}
