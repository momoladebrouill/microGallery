package studio.lunabee.amicrogallery.calendar

import studio.lunabee.compose.presenter.PresenterUiState

sealed interface CalendarUiState : PresenterUiState {
    val textUiShown: String
    data class Default(
        val count: Int,
    ) : CalendarUiState {
        override val textUiShown: String = "hello from Calendar drawer"
    }
}
