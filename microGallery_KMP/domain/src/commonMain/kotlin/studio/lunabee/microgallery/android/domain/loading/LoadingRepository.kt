package studio.lunabee.microgallery.android.domain.loading

import studio.lunabee.microgallery.android.data.Directory
import studio.lunabee.microgallery.android.data.Picture

interface LoadingRepository {
    suspend fun fetchRootNode()
    suspend fun getRootDir() : Directory
    suspend fun pictureDbFreshStart()
    suspend fun savePicturesInDb(pictures: List<Picture>)
}
