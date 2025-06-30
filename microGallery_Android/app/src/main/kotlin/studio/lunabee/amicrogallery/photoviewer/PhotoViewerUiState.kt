package studio.lunabee.amicrogallery.photoviewer

import studio.lunabee.compose.presenter.PresenterUiState
import studio.lunabee.microgallery.android.data.Picture

data class PhotoViewerUiState( // picture is not here at the beginning, it must be found in db
    val picture: Picture?,
) : PresenterUiState
