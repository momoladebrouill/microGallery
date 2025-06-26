package studio.lunabee.amicrogallery.untimed

import studio.lunabee.microgallery.android.data.Picture

sealed interface UntimedAction{
    data class GotTheList(
        val images : List<Picture>
    ) : UntimedAction
}
