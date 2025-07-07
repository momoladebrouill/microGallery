package studio.lunabee.amicrogallery.photoviewer

import studio.lunabee.compose.presenter.PresenterUiState
import studio.lunabee.microgallery.android.data.Picture

sealed interface PhotoViewerUiState : PresenterUiState {
    object Waiting : PhotoViewerUiState

    data class HasPicture(
        val picture: Picture,
    ) : PhotoViewerUiState
}
