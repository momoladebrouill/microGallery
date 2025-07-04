package studio.lunabee.microgallery.android.repository.impl

import studio.lunabee.amicrogallery.picture.PictureLocal
import studio.lunabee.microgallery.android.data.Directory
import studio.lunabee.microgallery.android.data.Picture
import studio.lunabee.microgallery.android.domain.loading.LoadingRepository
import studio.lunabee.microgallery.android.repository.datasource.remote.TreeRemoteDatasource

class LoadingRepositoryImpl(
    val treeRemoteDatasource: TreeRemoteDatasource,
    val pictureLocal: PictureLocal,
) : LoadingRepository {
    override suspend fun fetchRootNode() {
        treeRemoteDatasource.fetchRoot()
    }

    override suspend fun getRootDir() : Directory {
        val rootNode: Directory = treeRemoteDatasource.getRoot() as Directory
        return rootNode
    }

    override suspend fun pictureDbFreshStart() {
        pictureLocal.freshStart()
    }

    override suspend fun savePicturesInDb(pictures: List<Picture>) {
        pictureLocal.insertPictures(pictures)
    }

}
