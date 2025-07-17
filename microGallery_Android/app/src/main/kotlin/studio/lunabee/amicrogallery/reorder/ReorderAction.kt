package studio.lunabee.amicrogallery.reorder

import studio.lunabee.microgallery.android.data.GameParameters

sealed interface ReorderAction {
    sealed interface ReorderGamingAction : ReorderAction
    sealed interface ReorderMenuAction : ReorderAction
    data class PutPicture(
        val index: Float,
        val url: String,
    ) : ReorderGamingAction

    data object JumpToGaming : ReorderMenuAction
}
