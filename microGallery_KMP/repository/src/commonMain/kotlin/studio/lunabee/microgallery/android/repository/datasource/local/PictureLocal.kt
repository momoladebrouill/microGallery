package studio.lunabee.microgallery.android.repository.datasource.local

import kotlinx.coroutines.flow.Flow
import studio.lunabee.microgallery.android.data.MMonth
import studio.lunabee.microgallery.android.data.MYear
import studio.lunabee.microgallery.android.data.MicroPicture
import studio.lunabee.microgallery.android.data.Picture
import studio.lunabee.microgallery.android.data.YearPreview

interface PictureLocal {
    suspend fun freshStart()
    suspend fun insertPictures(pictures: List<Picture>)
    fun getYearPreviews(): Flow<List<YearPreview>>
    fun getMonthsInYear(year: MYear): Flow<List<MMonth>>
    fun getPicturesInMonth(year: MYear, month: MMonth): Flow<List<MicroPicture>>
    fun getPicturesUntimed(): Flow<List<MicroPicture>>
    fun getPictureById(id: Long): Flow<MicroPicture>
}
