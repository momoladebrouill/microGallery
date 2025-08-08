package studio.lunabee.microgallery.android.domain.loading

import kotlinx.coroutines.flow.Flow
import studio.lunabee.microgallery.android.data.Directory
import studio.lunabee.microgallery.android.data.MYear
import studio.lunabee.microgallery.android.data.Picture

interface LoadingRepository {
    suspend fun getYears(): List<MYear>
    fun getYearsAsFlow(years: List<MYear>): Flow<Directory>
    fun yearsInDb(): Flow<List<MYear>>
    suspend fun pictureDbFreshStart()
    suspend fun isPictureDbEmpty(): Boolean
    suspend fun savePicturesInDb(pictures: List<Picture>)
    fun setBaseRemoteUrl(url: String)
}
