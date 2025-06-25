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
        val rootNode: Node = rootService.fetchRootList()[0].toData()
        rootNodeCache = giveFullNameToFiles(node = rootNode)
    }

    override fun getRoot() : Node {
        if(rootNodeCache == null)
            throw CoreError("trying to get Root Node but not fetched")
        return rootNodeCache!!
    }
}

fun giveFullNameToFiles(node: Node, path: String = "", year:String = "", month:String =""): Node {
    return when (node) {
        is Directory -> Directory(
            name = node.name,
            content = node.content.map { child: Node ->
                giveFullNameToFiles(
                    node = child,
                    path = "$path/${node.name}",
                    year = if(year == "") node.name else year, //if year is not defined, it's a year directory
                    month = if(month == "" && year != "") node.name else month //if year is definied but not month, it's a month
                ) },
        )
        is Picture -> {
            val fullPath = "$path/${node.name}"
            Picture(
                id=5L,
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
