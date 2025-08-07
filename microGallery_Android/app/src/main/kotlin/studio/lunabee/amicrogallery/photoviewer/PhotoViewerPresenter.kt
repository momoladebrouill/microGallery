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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import studio.lunabee.amicrogallery.photoviewer.reducers.PhotoViewerHasPhotoReducer
import studio.lunabee.amicrogallery.photoviewer.reducers.PhotoViewerWaitingReducer
import studio.lunabee.compose.presenter.LBPresenter
import studio.lunabee.compose.presenter.LBSimpleReducer
import studio.lunabee.microgallery.android.domain.photoviewer.usecase.GetNeighborsByPictureUseCase
import studio.lunabee.microgallery.android.domain.photoviewer.usecase.ObservePictureByIdUseCase

class PhotoViewerPresenter(
    savedStateHandle: SavedStateHandle,
    val observePictureByIdUseCase: ObservePictureByIdUseCase,
) : LBSinglePresenter<PhotoViewerUiState, PhotoViewerNavScope, PhotoViewerAction>() {

    private val params: PhotoViewerDestination = savedStateHandle.toRoute()
    val pictureById = observePictureByIdUseCase(params.pictureId).map { PhotoViewerAction.FoundPicture(it) }

    override val flows: List<Flow<PhotoViewerAction>> = listOf(
        pictureById,
    )

    override fun initReducer(): LBSingleReducer<PhotoViewerUiState, PhotoViewerNavScope, PhotoViewerAction> {
        return PhotoViewerReducer(
            coroutineScope = viewModelScope,
            emitUserAction = ::emitUserAction,
        )
    }

    init {
        getPictureById(params.pictureId)
    }

    fun getPictureById(pictureId: Long) {
        viewModelScope.launch {
            val pic: MicroPicture = observePictureByIdUseCase(pictureId).first()
            emitUserAction(PhotoViewerAction.FoundPicture(pic))
        }
    }

    override fun getInitialState(): PhotoViewerUiState = PhotoViewerUiState.Waiting
    override fun getReducerByState(actualState: PhotoViewerUiState):
        LBSimpleReducer<PhotoViewerUiState, PhotoViewerNavScope, PhotoViewerAction> {
        return when (actualState) {
            // TODO : combine reducers for "GetPictures"
            is PhotoViewerUiState.Waiting -> PhotoViewerWaitingReducer(
                coroutineScope = viewModelScope,
                emitUserAction = ::emitUserAction,
                observePictureByIdUseCase = observePictureByIdUseCase,
                getNeighborsByPictureUseCase = getNeighborsByPictureUseCase,
            )

    override fun getInitialState(): PhotoViewerUiState = PhotoViewerUiState(
        picture = null,
        loading = false,
        share = ::emitShare,
        stopLoading = { emitUserAction(PhotoViewerAction.StopLoading) },
    )

    override val content: @Composable (PhotoViewerUiState) -> Unit = { PhotoViewerScreen(it) }
}
