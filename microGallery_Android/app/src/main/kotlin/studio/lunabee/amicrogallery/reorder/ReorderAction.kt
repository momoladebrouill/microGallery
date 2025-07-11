package studio.lunabee.amicrogallery.reorder

sealed interface ReorderAction {
    data class PutPicture(
        val index: Float,
        val url: String,
    ) : ReorderAction
}
