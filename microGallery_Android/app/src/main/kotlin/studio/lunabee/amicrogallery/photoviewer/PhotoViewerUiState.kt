package studio.lunabee.amicrogallery.photoviewer

import studio.lunabee.compose.presenter.PresenterUiState
import studio.lunabee.microgallery.android.data.Picture

data class PhotoViewerUiState(
    val picture: Picture?
) : PresenterUiState