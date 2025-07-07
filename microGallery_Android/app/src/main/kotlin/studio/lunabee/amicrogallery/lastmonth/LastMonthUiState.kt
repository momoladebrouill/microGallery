package studio.lunabee.amicrogallery.lastmonth

import studio.lunabee.compose.presenter.PresenterUiState
import studio.lunabee.microgallery.android.data.MicroPicture
import studio.lunabee.microgallery.android.data.Picture

data class LastMonthUiState(
    val pictures: List<MicroPicture>,
    val showPhoto: (Long) -> Unit,
) : PresenterUiState
