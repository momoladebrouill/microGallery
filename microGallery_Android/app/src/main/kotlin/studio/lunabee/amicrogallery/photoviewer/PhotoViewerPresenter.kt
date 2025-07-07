package studio.lunabee.amicrogallery.photoviewer

import androidx.compose.runtime.Composable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import studio.lunabee.amicrogallery.photoviewer.reducers.PhotoViewerHasPhotoReducer
import studio.lunabee.amicrogallery.photoviewer.reducers.PhotoViewerWaitingReducer
import studio.lunabee.compose.presenter.LBPresenter
import studio.lunabee.compose.presenter.LBSimpleReducer
import studio.lunabee.microgallery.android.domain.photoviewer.usecase.ObservePictureByIdUseCase

class PhotoViewerPresenter(
    savedStateHandle: SavedStateHandle,
    val observePictureByIdUseCase: ObservePictureByIdUseCase,
) : LBPresenter<PhotoViewerUiState, PhotoViewerNavScope, PhotoViewerAction>() {

    private val params: PhotoViewerDestination = savedStateHandle.toRoute()
    val pictureById = observePictureByIdUseCase(params.pictureId).map { PhotoViewerAction.FoundPicture(it) }

    override val flows: List<Flow<PhotoViewerAction>> = listOf(
        pictureById,
    )

    override fun getInitialState(): PhotoViewerUiState = PhotoViewerUiState.Waiting
    override fun getReducerByState(actualState: PhotoViewerUiState): LBSimpleReducer<PhotoViewerUiState, PhotoViewerNavScope, PhotoViewerAction> {
        return when (actualState) {
            is PhotoViewerUiState.Waiting -> PhotoViewerWaitingReducer(
                coroutineScope = viewModelScope,
                emitUserAction = ::emitUserAction
            )

            is PhotoViewerUiState.HasPicture -> PhotoViewerHasPhotoReducer(
                coroutineScope = viewModelScope,
                emitUserAction = ::emitUserAction
            )
        }
    }

    override val content: @Composable (PhotoViewerUiState) -> Unit = { PhotoViewerScreen(it) }
}
