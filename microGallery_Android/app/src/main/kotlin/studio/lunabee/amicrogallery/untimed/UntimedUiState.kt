package studio.lunabee.amicrogallery.untimed

import studio.lunabee.compose.presenter.PresenterUiState

sealed interface UntimedUiState : PresenterUiState {
    val textUiShown: String
    data class Default(
        val count: Int,
    ) : UntimedUiState {
        override val textUiShown: String = "hello from untimed drawer"
    }
}
