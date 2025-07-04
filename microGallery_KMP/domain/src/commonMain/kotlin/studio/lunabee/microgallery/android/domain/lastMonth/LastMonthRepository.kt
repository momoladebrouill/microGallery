package studio.lunabee.microgallery.android.domain.lastMonth

import kotlinx.coroutines.flow.Flow
import studio.lunabee.microgallery.android.data.MMonth
import studio.lunabee.microgallery.android.data.MYear
import studio.lunabee.microgallery.android.data.Picture

interface LastMonthRepository {
     fun getLastMonthPictures(year: MYear, month: MMonth): Flow<List<Picture>>
}
