package studio.lunabee.microgallery.android.domain.reorder

import studio.lunabee.microgallery.android.data.MYear
import studio.lunabee.microgallery.android.data.MicroPicture

interface ReorderRepository {
    suspend fun getRandomInYear(year : MYear) : MicroPicture
}