package studio.lunabee.microgallery.android.repository.impl

import studio.lunabee.amicrogallery.picture.PictureLocal
import studio.lunabee.microgallery.android.data.Directory
import studio.lunabee.microgallery.android.data.Node
import studio.lunabee.microgallery.android.data.Picture
import studio.lunabee.microgallery.android.domain.loading.LoadingRepository
import studio.lunabee.microgallery.android.repository.datasource.remote.TreeRemoteDatasource

class LoadingRepositoryImpl(
    val treeRemoteDatasource: TreeRemoteDatasource,
    val pictureLocal: PictureLocal,
) : LoadingRepository {
    override suspend fun fetchRootNode() {
        treeRemoteDatasource.fetchRoot()
        val rootNode : Node = treeRemoteDatasource.getRoot()
        storeNodeLocally(rootNode)
    }

    suspend fun storeNodeLocally(node: Node){
        when(node){
            is Picture -> pictureLocal.insertPicture(node)
            is Directory -> {
                for(child in node.content){
                    storeNodeLocally(child) // TODO : better
                }
            }
        }
    }

}