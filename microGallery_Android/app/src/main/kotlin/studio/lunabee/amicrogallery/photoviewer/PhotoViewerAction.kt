package studio.lunabee.amicrogallery.photoviewer

import android.content.Context
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import studio.lunabee.microgallery.android.data.MicroPicture
import studio.lunabee.microgallery.android.data.Picture

sealed interface PhotoViewerAction {
    data class FoundPicture(
        val picture: MicroPicture,
    ) : PhotoViewerAction

    data class SharePicture(
        val context: Context,
        val launcher: ManagedActivityResultLauncher<Intent, ActivityResult>,
    ) : PhotoViewerAction

    object StopLoading : PhotoViewerAction
}
