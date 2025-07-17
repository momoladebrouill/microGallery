package studio.lunabee.amicrogallery.reorder

import studio.lunabee.amicrogallery.utils.LineMap
import studio.lunabee.compose.presenter.PresenterUiState

sealed interface ReorderUiState : PresenterUiState {
    data class ReorderGamingUiState(
        val picturesQQty: Int,
        val picturesNotPlaced: Set<String>,
        val picturesInSlots: LineMap<String?>, // ensemble de placement
        val putPicture: (Float, String) -> Unit,
    ) : ReorderUiState

    data class ReorderMenuUiState(
        val pictures : Set<String>,
        val time : Int,
        val jumpToGaming: () -> Unit
    ) : ReorderUiState
}
