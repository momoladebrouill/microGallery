package studio.lunabee.microgallery.android.domain.lastMonth

import studio.lunabee.microgallery.android.data.Picture

interface LastMonthRepository {
    suspend fun getLastMonthPictures(year : String, month: String) : List<Picture>
}