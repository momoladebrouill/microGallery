package studio.lunabee.microgallery.android.domain.loading

import kotlinx.coroutines.flow.Flow
import studio.lunabee.microgallery.android.data.Directory
import studio.lunabee.microgallery.android.data.Picture

interface LoadingRepository {

    fun getRootDir() : Flow<Directory>
    suspend fun pictureDbFreshStart()
    suspend fun savePicturesInDb(pictures: List<Picture>)
}
