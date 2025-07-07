package studio.lunabee.amicrogallery.photoviewer

import androidx.compose.runtime.Composable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import studio.lunabee.compose.presenter.LBSinglePresenter
import studio.lunabee.compose.presenter.LBSingleReducer
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

    override fun getInitialState(): PhotoViewerUiState = PhotoViewerUiState(null)

    override val content: @Composable (PhotoViewerUiState) -> Unit = { PhotoViewerScreen(it) }
}
