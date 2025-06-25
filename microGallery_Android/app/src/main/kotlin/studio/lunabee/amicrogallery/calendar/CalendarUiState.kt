package studio.lunabee.amicrogallery.calendar

import studio.lunabee.compose.presenter.PresenterUiState
import studio.lunabee.microgallery.android.data.Node

data class CalendarUiState(
    val rootNode: Node,
) : PresenterUiState
