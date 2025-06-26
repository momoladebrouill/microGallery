package studio.lunabee.amicrogallery.picture

import kotlinx.coroutines.flow.Flow
import studio.lunabee.microgallery.android.data.Picture

interface PictureLocal {
    suspend fun insertPictures(pictures: List<Picture>)
    suspend fun insertPicture(picture : Picture)
    fun getPictures(): Flow<List<Picture>>
    suspend fun getYears(): List<String>
    suspend fun freshStart()
    suspend fun getMonthsInYear(year : String) : List<String>
    suspend fun getPicturesInMonth(year : String, month : String) : List<Picture>
    suspend fun getPicturesUntimed() : List<Picture>
}