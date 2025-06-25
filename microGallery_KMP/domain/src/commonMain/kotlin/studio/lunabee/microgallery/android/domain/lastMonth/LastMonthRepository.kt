package studio.lunabee.microgallery.android.domain.lastMonth

import studio.lunabee.microgallery.android.data.Picture

interface LastMonthRepository {
    fun getLastMonthPictures() : List<Picture>
}