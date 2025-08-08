package studio.lunabee.amicrogallery.photoviewer

<<<<<<<<< Temporary merge branch 1
import studio.lunabee.microgallery.android.data.MicroPicture
=========
import android.content.Context
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import studio.lunabee.microgallery.android.data.MicroPicture
import studio.lunabee.microgallery.android.domain.photoviewer.UrlIndex

sealed interface PhotoViewerAction {
    data class FoundPicture(
        val picture: MicroPicture,
    ) : PhotoViewerAction

    data class SharePicture(
        val context: Context,
        val launcher: ManagedActivityResultLauncher<Intent, ActivityResult>,
        val index: UrlIndex,
    ) : PhotoViewerAction

    object StopLoading : PhotoViewerAction
}
