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
        val rootNode : Directory = treeRemoteDatasource.getRoot() as Directory
        pictureLocal.freshStart()
        for(year in rootNode.content){
            val yearDir : Directory = year as Directory
            if(year.name == "untimed")
                pictureLocal.insertPictures(yearDir.content.map { it as Picture })
            else{
                for(month in yearDir.content){
                    val monthDir = month as Directory
                    pictureLocal.insertPictures(monthDir.content.map { it as Picture })
                }
            }
        }
    }
}