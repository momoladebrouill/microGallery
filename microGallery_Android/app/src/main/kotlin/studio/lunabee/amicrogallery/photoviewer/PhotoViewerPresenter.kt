package studio.lunabee.amicrogallery.photoviewer

import android.content.Context
import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.compose.runtime.Composable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import studio.lunabee.amicrogallery.photoviewer.reducers.PhotoViewerHasPhotoReducer
import studio.lunabee.amicrogallery.photoviewer.reducers.PhotoViewerWaitingReducer
import studio.lunabee.compose.presenter.LBPresenter
import studio.lunabee.compose.presenter.LBSimpleReducer
import studio.lunabee.compose.presenter.LBSinglePresenter
import studio.lunabee.compose.presenter.LBSingleReducer
import studio.lunabee.microgallery.android.data.MicroPicture
import studio.lunabee.microgallery.android.domain.photoviewer.usecase.GetNeighborsByPictureUseCase
import studio.lunabee.microgallery.android.domain.photoviewer.usecase.ObservePictureByIdUseCase

class PhotoViewerPresenter(
    savedStateHandle: SavedStateHandle,
    val observePictureByIdUseCase: ObservePictureByIdUseCase,
    val getNeighborsByPictureUseCase: GetNeighborsByPictureUseCase
) : LBPresenter<PhotoViewerUiState, PhotoViewerNavScope, PhotoViewerAction>() {

    private val params: PhotoViewerDestination = savedStateHandle.toRoute()

    init {
        emitUserAction(PhotoViewerAction.GetPictures(params.pictureId))
    }


    override fun getInitialState(): PhotoViewerUiState = PhotoViewerUiState.Waiting

    override fun getReducerByState(actualState: PhotoViewerUiState): LBSimpleReducer<PhotoViewerUiState, PhotoViewerNavScope, PhotoViewerAction> {
        return when (actualState) {
            // TODO : combine reducers for "GetPictures"
            is PhotoViewerUiState.Waiting -> PhotoViewerWaitingReducer(
                coroutineScope = viewModelScope,
                emitUserAction = ::emitUserAction,
                observePictureByIdUseCase = observePictureByIdUseCase,
                getNeighborsByPictureUseCase = getNeighborsByPictureUseCase,
            )

            is PhotoViewerUiState.HasPicture -> PhotoViewerHasPhotoReducer(
                coroutineScope = viewModelScope,
                emitUserAction = ::emitUserAction,
                observePictureByIdUseCase = observePictureByIdUseCase,
                getNeighborsByPictureUseCase = getNeighborsByPictureUseCase
            )
        }
    }

    override val flows: List<Flow<PhotoViewerAction>> = emptyList()

    override val content: @Composable (PhotoViewerUiState) -> Unit = { PhotoViewerScreen(it) }
}
