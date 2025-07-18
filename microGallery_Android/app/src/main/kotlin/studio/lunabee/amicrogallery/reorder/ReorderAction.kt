package studio.lunabee.amicrogallery.reorder

import studio.lunabee.microgallery.android.data.MicroPicture

sealed interface ReorderAction {
    sealed interface ReorderGamingAction : ReorderAction
    sealed interface ReorderMenuAction : ReorderAction
    data class PutPicture(
        val index: Float,
        val picture: MicroPicture
    ) : ReorderGamingAction

    data class JumpToPicture(
        val pictureId : Long
    ) : ReorderGamingAction

    object WantToJumpToGaming : ReorderMenuAction
    data class JumpToGaming(
        val pictures: Set<MicroPicture>
    ) : ReorderMenuAction

    data class SetQty(
        val qty : Int
    ) : ReorderMenuAction
}
