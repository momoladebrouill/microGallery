package studio.lunabee.amicrogallery.picture

import studio.lunabee.microgallery.android.data.Picture
import studio.lunabee.microgallery.android.data.YearPreview

interface PictureLocal {
    suspend fun freshStart()

    suspend fun insertPictures(pictures: List<Picture>)

    suspend fun getYearPreviews(): List<YearPreview>

    suspend fun getMonthsInYear(year: String): List<String>
    suspend fun getPicturesInMonth(year: String, month: String): List<Picture>
    suspend fun getPicturesUntimed(): List<Picture>
    suspend fun getPictureById(id: Long): Picture
}
