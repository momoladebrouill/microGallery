package studio.lunabee.microgallery.android.remote.datasource

import studio.lunabee.microgallery.android.domain.Directory
import studio.lunabee.microgallery.android.domain.Node
import studio.lunabee.microgallery.android.domain.Picture
import studio.lunabee.microgallery.android.remote.service.RootService
import studio.lunabee.microgallery.android.repository.datasource.remote.TreeRemoteDatasource

class TreeRemoteDatasourceImpl(
    private val rootService: RootService
) : TreeRemoteDatasource{
    override suspend fun fetchRoot() : Node {
        val rootNode : Node =  rootService.fetchRootList()[0].toData()
        return giveFullNameToFiles(node = rootNode, path="")

    }
}

fun giveFullNameToFiles(node: Node, path: String) : Node{
    return when(node){
        is Directory -> Directory(
            name = node.name,
            content = node.content.map {child : Node -> giveFullNameToFiles(child, "$path/${node.name}")}
        )
        is Picture -> Picture(
            name = node.name,
            fullPath = "$path/${node.name}"
        )
    }
}