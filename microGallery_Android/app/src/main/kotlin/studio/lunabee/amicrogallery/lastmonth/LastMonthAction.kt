package studio.lunabee.amicrogallery.lastmonth

import studio.lunabee.microgallery.android.data.Picture

sealed interface LastMonthAction{
    data class GotTheList(
        val pictures : List<Picture>
    ) : LastMonthAction
}
