package studio.lunabee.amicrogallery.untimed

import studio.lunabee.microgallery.android.data.MicroPicture

sealed interface UntimedAction {
    data class GotTheList(
        val images: List<MicroPicture>,
    ) : UntimedAction

    data class ShowPhoto(
        val photoId: Long,
    ) : UntimedAction
}
