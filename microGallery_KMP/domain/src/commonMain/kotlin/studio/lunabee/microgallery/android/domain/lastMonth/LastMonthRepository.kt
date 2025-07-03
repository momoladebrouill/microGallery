package studio.lunabee.microgallery.android.domain.lastMonth

import studio.lunabee.microgallery.android.data.MMonth
import studio.lunabee.microgallery.android.data.MYear
import studio.lunabee.microgallery.android.data.Picture

interface LastMonthRepository {
    suspend fun getLastMonthPictures(year: MYear, month: MMonth): List<Picture>
}
