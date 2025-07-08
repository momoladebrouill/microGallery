package studio.lunabee.amicrogallery.photoviewer.reducers

import android.content.Context
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import studio.lunabee.amicrogallery.photoviewer.PhotoViewerAction
import studio.lunabee.amicrogallery.photoviewer.PhotoViewerNavScope
import studio.lunabee.amicrogallery.photoviewer.PhotoViewerUiState
import studio.lunabee.compose.presenter.LBReducer
import studio.lunabee.compose.presenter.ReduceResult
import studio.lunabee.compose.presenter.asResult
import studio.lunabee.compose.presenter.withSideEffect
import studio.lunabee.microgallery.android.domain.photoviewer.usecase.GetNeighborsByPictureUseCase
import studio.lunabee.microgallery.android.domain.photoviewer.usecase.ObservePictureByIdUseCase

class PhotoViewerWaitingReducer(
    override val coroutineScope: CoroutineScope,
    override val emitUserAction: (PhotoViewerAction) -> Unit,
    val observePictureByIdUseCase: ObservePictureByIdUseCase,
    val getNeighborsByPictureUseCase: GetNeighborsByPictureUseCase,
) : LBReducer<PhotoViewerUiState.Waiting, PhotoViewerUiState, PhotoViewerNavScope, PhotoViewerAction, PhotoViewerAction.WaitingAction>() {

    fun emitShare(context: Context, launcher: ManagedActivityResultLauncher<Intent, ActivityResult>) =
        emitUserAction(PhotoViewerAction.SharePicture(context, launcher))

    @OptIn(ExperimentalFoundationApi::class)
    override suspend fun reduce(
        actualState: PhotoViewerUiState.Waiting,
        action: PhotoViewerAction.WaitingAction,
        performNavigation: (PhotoViewerNavScope.() -> Unit) -> Unit,
    ): ReduceResult<PhotoViewerUiState> {
        return when (action) {
            is PhotoViewerAction.GetPictures -> actualState withSideEffect {
                val picture = observePictureByIdUseCase(action.centerId).first()
                val neighbors = getNeighborsByPictureUseCase(picture.id)
                emitUserAction(PhotoViewerAction.FoundPictures(picture, neighbors))
            }

            is PhotoViewerAction.FoundPictures -> PhotoViewerUiState.HasPicture(
                picture = action.picture,
                neighbors = action.neighbors,
                share = ::emitShare,
                stopLoading = { emitUserAction(PhotoViewerAction.StopLoading) },
                switchTo = { emitUserAction(PhotoViewerAction.GetPictures(it)) },
                pagerState = PagerState(
                    currentPage = 1,
                    pageCount = { 3 },
                ),
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
