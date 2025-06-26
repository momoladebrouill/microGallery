package studio.lunabee.microgallery.android.remote.datasource

import studio.lunabee.amicrogallery.android.error.CoreError
import studio.lunabee.microgallery.android.data.Directory
import studio.lunabee.microgallery.android.data.Node
import studio.lunabee.microgallery.android.data.Picture
import studio.lunabee.microgallery.android.remote.service.RootService
import studio.lunabee.microgallery.android.repository.datasource.remote.TreeRemoteDatasource

class TreeRemoteDatasourceImpl(
    private val rootService: RootService,
) : TreeRemoteDatasource {

    private var rootNodeCache : Node? = null

    override suspend fun fetchRoot(){
        val rootNode: Directory = rootService.fetchRootList()[0].toData() as Directory

        rootNodeCache =
            Directory(
                name = "0",
                content = rootNode.content.map(::giveFullNameToFiles)
            )

    }

    override fun getRoot(): Node {
        return rootNodeCache ?: throw CoreError("Attempted to retrieve root node, but it has not been fetched.")
    }

}

fun giveFullNameToFiles(node: Node, path: String= "/disque/photos/ranged", year:String? = null, month:String? = null ): Node {
    return when (node) {
        is Directory -> Directory(
            name = node.name,
            content = node.content.map { child: Node ->
                giveFullNameToFiles(
                    node = child,
                    path = "$path/${node.name}",
                    year = year ?: node.name , //if year is not defined, it's a year directory
                    month = if(year != null) node.name else month //if year is definied but not month, it's a month
                ) },
        )
        is Picture -> {
            val fullPath = "$path/${node.name}"
            Picture(
                id=0,
                name = node.name,
                fullResPath = fullPath,
                lowResPath = fullPath
                    // low res is in another directory named ranged_70percent following same architecture
                    .replace("/ranged/", "/ranged70_percent/")
                    // low res is a webp instead of JPG/jpg
                    .replace(Regex("(?i)\\.jpg$"), ".webp"),
                year = year,
                month = month
            )
        }
    }
}
