package studio.lunabee.amicrogallery.photoviewer

import android.content.Context
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import studio.lunabee.microgallery.android.data.MicroPicture

sealed interface PhotoViewerAction {

    sealed interface WaitingAction : PhotoViewerAction
    sealed interface HasPictureAction : PhotoViewerAction
    data class FoundPicture(
        val picture: MicroPicture,
    ) : WaitingAction

    data class SharePicture(
        val context: Context,
        val launcher: ManagedActivityResultLauncher<Intent, ActivityResult>,
    ) : HasPictureAction

    object StopLoading : HasPictureAction
}
