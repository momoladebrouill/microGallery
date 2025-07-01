package studio.lunabee.amicrogallery.untimed
import studio.lunabee.compose.presenter.PresenterUiState
import studio.lunabee.microgallery.android.data.Picture

data class UntimedUiState(
    val images: List<Picture>,
) : PresenterUiState
