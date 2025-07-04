package studio.lunabee.amicrogallery.picture

import kotlinx.coroutines.flow.Flow
import studio.lunabee.microgallery.android.data.MMonth
import studio.lunabee.microgallery.android.data.MYear
import studio.lunabee.microgallery.android.data.Picture
import studio.lunabee.microgallery.android.data.YearPreview

interface PictureLocal {
    suspend fun freshStart()
    suspend fun insertPictures(pictures: List<Picture>)
    fun getYearPreviews(): Flow<List<YearPreview>>
    fun getMonthsInYear(year: MYear): Flow<List<MMonth>>
    fun getPicturesInMonth(year: MYear, month: MMonth): Flow<List<Picture>>
    fun getPicturesUntimed(): Flow<List<Picture>>
    fun getPictureById(id: Long): Flow<Picture>
}
