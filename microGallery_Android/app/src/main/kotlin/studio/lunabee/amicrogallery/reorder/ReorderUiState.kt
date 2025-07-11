package studio.lunabee.amicrogallery.reorder
import studio.lunabee.amicrogallery.utils.LineMap
import studio.lunabee.compose.presenter.PresenterUiState

data class ReorderUiState(
    val picturesQQty : Int,
    val picturesNotPlaced : Set<String>,
    val picturesInSlots : LineMap<String?>, // ensemble de couplages
    val putPicture: (Float, String) -> Unit
) : PresenterUiState