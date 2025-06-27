package studio.lunabee.amicrogallery.lastmonth

import studio.lunabee.compose.presenter.PresenterUiState
import studio.lunabee.microgallery.android.data.Picture

data class LastMonthUiState(val pictures : List<Picture>) : PresenterUiState