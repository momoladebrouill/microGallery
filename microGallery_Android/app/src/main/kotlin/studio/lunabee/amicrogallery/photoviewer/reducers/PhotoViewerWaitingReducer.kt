package studio.lunabee.amicrogallery.photoviewer.reducers

import android.content.Context
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import kotlinx.coroutines.CoroutineScope
import studio.lunabee.amicrogallery.photoviewer.PhotoViewerAction
import studio.lunabee.amicrogallery.photoviewer.PhotoViewerNavScope
import studio.lunabee.amicrogallery.photoviewer.PhotoViewerUiState
import studio.lunabee.compose.presenter.LBReducer
import studio.lunabee.compose.presenter.ReduceResult
import studio.lunabee.compose.presenter.asResult

class PhotoViewerWaitingReducer(
    override val coroutineScope: CoroutineScope,
    override val emitUserAction: (PhotoViewerAction) -> Unit,
) : LBReducer<PhotoViewerUiState.Waiting, PhotoViewerUiState, PhotoViewerNavScope, PhotoViewerAction, PhotoViewerAction.WaitingAction>() {

    fun emitShare(context: Context, launcher: ManagedActivityResultLauncher<Intent, ActivityResult>) =
        emitUserAction(PhotoViewerAction.SharePicture(context, launcher))

    override suspend fun reduce(
        actualState: PhotoViewerUiState.Waiting,
        action: PhotoViewerAction.WaitingAction,
        performNavigation: (PhotoViewerNavScope.() -> Unit) -> Unit,
    ): ReduceResult<PhotoViewerUiState> {
        return when (action) {
            is PhotoViewerAction.FoundPicture -> PhotoViewerUiState.HasPicture(
                picture = action.picture,
                share = ::emitShare,
                stopLoading = { emitUserAction(PhotoViewerAction.StopLoading) },
            ).asResult()
        }
    }

    override fun filterAction(action: PhotoViewerAction): Boolean {
        return action is PhotoViewerAction.WaitingAction
    }

    override fun filterUiState(actualState: PhotoViewerUiState): Boolean {
        return actualState is PhotoViewerUiState.Waiting
    }
}
