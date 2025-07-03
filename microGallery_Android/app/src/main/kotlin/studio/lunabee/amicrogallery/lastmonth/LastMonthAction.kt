package studio.lunabee.amicrogallery.lastmonth

import studio.lunabee.microgallery.android.data.Picture

sealed interface LastMonthAction {
    object GetTheList : LastMonthAction
    data class GotTheList(
        // got the list of pictures to show
        val pictures: List<Picture>,
    ) : LastMonthAction

    data class ShowPhoto(
        // jump to photo screen
        val photoId: Long,
    ) : LastMonthAction
}
