package studio.lunabee.microgallery.android.remote.datasource

import studio.lunabee.microgallery.android.domain.Directory
import studio.lunabee.microgallery.android.domain.Node
import studio.lunabee.microgallery.android.domain.Picture
import studio.lunabee.microgallery.android.remote.service.RootService
import studio.lunabee.microgallery.android.repository.datasource.remote.TreeRemoteDatasource

class TreeRemoteDatasourceImpl(
    private val rootService: RootService,
) : TreeRemoteDatasource {

    private var rootNodeCache : Node? = null

    override suspend fun fetchRoot(): Node {
        val rootNode: Node = rootService.fetchRootList()[0].toData()
        return giveFullNameToFiles(node = rootNode)
    }

    override suspend fun getRoot() : Node {
        if(rootNodeCache == null)
            rootNodeCache = fetchRoot()
        return rootNodeCache!!
    }
}

fun giveFullNameToFiles(node: Node, path: String = ""): Node {
    return when (node) {
        is Directory -> Directory(
            name = node.name,
            content = node.content.map { child: Node -> giveFullNameToFiles(child, "$path/${node.name}") },
        )
        is Picture -> {
            val fullPath = "$path/${node.name}"
            Picture(
                name = node.name,
                fullResPath = fullPath,
                lowResPath = fullPath
                    // low res is in another directory named ranged_70percent following same architecture
                    .replace("/ranged/", "/ranged70_percent/")
                    // low res is a webp instead of JPG/jpg
                    .replace(Regex("(?i)\\.jpg$"), ".webp"),
            )
        }
    }
}
