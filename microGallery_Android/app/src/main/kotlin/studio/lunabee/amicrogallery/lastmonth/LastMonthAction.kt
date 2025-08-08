package studio.lunabee.amicrogallery.lastmonth

import studio.lunabee.microgallery.android.data.MicroPicture

sealed interface LastMonthAction {
    data class GotTheList(
        // got the list of pictures to show
        val pictures: List<MicroPicture>,
    ) : LastMonthAction

    data class ShowPhoto(
        // jump to photo screen
        val photoId: Long,
    ) : LastMonthAction
}
