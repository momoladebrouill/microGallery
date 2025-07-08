package studio.lunabee.microgallery.android.repository.impl

import studio.lunabee.microgallery.android.data.Directory
import studio.lunabee.microgallery.android.data.Picture
import studio.lunabee.microgallery.android.domain.loading.LoadingRepository
import studio.lunabee.microgallery.android.repository.datasource.local.PictureLocal
import studio.lunabee.microgallery.android.repository.datasource.remote.TreeRemoteDatasource

class LoadingRepositoryImpl(
    val treeRemoteDatasource: TreeRemoteDatasource,
    val pictureLocal: PictureLocal,
) : LoadingRepository {
    override suspend fun getRootDir(): Directory {
        return treeRemoteDatasource.getRoot()
    }

    override suspend fun pictureDbFreshStart() {
        pictureLocal.freshStart()
    }

    override suspend fun savePicturesInDb(pictures: List<Picture>) {
        pictureLocal.insertPictures(pictures)
    }
}
