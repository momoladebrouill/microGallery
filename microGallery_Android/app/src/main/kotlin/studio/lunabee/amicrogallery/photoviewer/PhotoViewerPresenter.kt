package studio.lunabee.amicrogallery.photoviewer

import androidx.compose.runtime.Composable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import studio.lunabee.compose.presenter.LBSinglePresenter
import studio.lunabee.compose.presenter.LBSingleReducer
import studio.lunabee.microgallery.android.data.Picture
import studio.lunabee.microgallery.android.domain.photoviewer.PhotoViewerRepository

class PhotoViewerPresenter(
    savedStateHandle: SavedStateHandle,
    val photoViewerRepository: PhotoViewerRepository,
) : LBSinglePresenter<PhotoViewerUiState, PhotoViewerNavScope, PhotoViewerAction>() {

    private val params: PhotoViewerDestination = savedStateHandle.toRoute()

    override val flows: List<Flow<PhotoViewerAction>> = listOf()

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
            val pic: Picture = photoViewerRepository.getPictureById(pictureId)
            emitUserAction(PhotoViewerAction.FoundPicture(pic))
        }
    }

    override fun getInitialState(): PhotoViewerUiState = PhotoViewerUiState.Waiting

    override val content: @Composable (PhotoViewerUiState) -> Unit = { PhotoViewerScreen(it) }
}
