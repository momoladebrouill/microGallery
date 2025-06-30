package studio.lunabee.amicrogallery.photoviewer

import kotlinx.coroutines.CoroutineScope
import studio.lunabee.compose.presenter.LBSingleReducer
import studio.lunabee.compose.presenter.ReduceResult
import studio.lunabee.compose.presenter.asResult

class PhotoViewerReducer(
    override val coroutineScope: CoroutineScope,
    override val emitUserAction: (PhotoViewerAction) -> Unit,
) : LBSingleReducer<PhotoViewerUiState, PhotoViewerNavScope, PhotoViewerAction>() {
    override suspend fun reduce(
        actualState: PhotoViewerUiState,
        action: PhotoViewerAction,
        performNavigation: (PhotoViewerNavScope.() -> Unit) -> Unit,
    ): ReduceResult<PhotoViewerUiState> {
        return when (action) {
            is PhotoViewerAction.FoundPicture -> actualState.copy(picture = action.picture).asResult()
        }
    }
}
