package studio.lunabee.amicrogallery.untimed

import studio.lunabee.compose.presenter.PresenterUiState
import studio.lunabee.microgallery.android.data.MicroPicture

data class UntimedUiState(
    val images: List<MicroPicture>,
    val showPhoto: (Long) -> Unit,
) : PresenterUiState
