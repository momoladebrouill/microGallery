package studio.lunabee.amicrogallery.lastmonth

import studio.lunabee.compose.presenter.PresenterUiState

sealed interface LastMonthUiState : PresenterUiState {
    val textUiShown: String
    data class Default(
        val count: Int,
    ) : LastMonthUiState {
        override val textUiShown: String = "hello from lastMonth drawer"
    }
}
