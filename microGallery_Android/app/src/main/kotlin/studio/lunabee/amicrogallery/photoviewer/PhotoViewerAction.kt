package studio.lunabee.amicrogallery.photoviewer

import android.content.Context
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import studio.lunabee.microgallery.android.data.MicroPicture
import studio.lunabee.microgallery.android.domain.photoviewer.UrlIndex
import studio.lunabee.microgallery.android.domain.photoviewer.usecase.GetNeighborsByPictureUseCase

sealed interface PhotoViewerAction {

    sealed interface WaitingAction : PhotoViewerAction
    sealed interface HasPictureAction : PhotoViewerAction
    data class FoundPicture(
        val picture: MicroPicture,
    ) :  PhotoViewerAction

    data class SharePicture(
        val context: Context,
        val launcher: ManagedActivityResultLauncher<Intent, ActivityResult>,
    ) : HasPictureAction

    data class GetPictures(
        val centerId : Long
    )  : HasPictureAction, WaitingAction
    data class FoundPictures(
        val picture : MicroPicture,
        val neighbors :  Pair<MicroPicture, MicroPicture>
    ) : HasPictureAction, WaitingAction

    object StopLoading :  HasPictureAction
}
