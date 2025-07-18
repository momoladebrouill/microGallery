package studio.lunabee.amicrogallery.reorder

import androidx.compose.ui.geometry.Offset
import studio.lunabee.amicrogallery.utils.LineMap
import studio.lunabee.compose.presenter.PresenterUiState
import studio.lunabee.microgallery.android.data.MicroPicture

sealed interface ReorderUiState : PresenterUiState {

    data class ReorderGamingUiState(
        val picturesQQty: Int,
        val pictureMap : Map<Long, MicroPicture>, // give the picture by id
        val picturesNotPlaced: Set<MicroPicture>,
        val picturesInSlots: LineMap<MicroPicture?>, // placing set
        val putPicture: (Float, MicroPicture) -> Unit,
        val jumpToPicture : (Long) -> Unit
    ) : ReorderUiState

    data class ReorderMenuUiState(
        val qty : Int,
        val time : Int,
        val isJumpingToGame : Boolean,
        val jumpToGaming: () -> Unit,
        val setQty: (Int) -> Unit
    ) : ReorderUiState
}
