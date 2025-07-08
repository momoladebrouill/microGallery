package studio.lunabee.microgallery.android.remote.datasource

import studio.lunabee.microgallery.android.data.Directory
import studio.lunabee.microgallery.android.data.MMonth
import studio.lunabee.microgallery.android.data.MYear
import studio.lunabee.microgallery.android.data.Node
import studio.lunabee.microgallery.android.data.Picture
import studio.lunabee.microgallery.android.remote.service.RootService
import studio.lunabee.microgallery.android.repository.datasource.remote.TreeRemoteDatasource

class TreeRemoteDatasourceImpl(
    private val rootService: RootService,
) : TreeRemoteDatasource {

    override suspend fun getRoot(): Directory {
        val root: Directory = rootService.fetchRootList()[0].toData() as Directory
        return root.copy(content = root.content.map { node -> giveFullNameToFiles(node) })
    }
}

// TODO : get the string of initial path another way
fun giveFullNameToFiles(node: Node, path: String = "/disque/photos/ranged", year: MYear? = null, month: MMonth? = null): Node {
    return when (node) {
        is Directory -> Directory(
            name = node.name,
            content = node.content.map { child: Node ->
                giveFullNameToFiles(
                    node = child,
                    path = "$path/${node.name}",
                    year = year ?: node.name, // if year is not defined, it's a year directory
                    month = if (year != null) node.name else month, // if year is definied but not month, it's a month
                )
            },
        )

        is Picture -> {
            val fullPath = "$path/${node.name}"
            Picture(
                id = 0,
                name = node.name,
                fullResPath = fullPath,
                lowResPath = fullPath
                    // low res is in another directory named ranged_70percent following same architecture
                    .replace("/ranged/", "/ranged70_percent/")
                    // low res is a webp instead of JPG/jpg
                    .replace(Regex("(?i)\\.jpg$"), ".webp"),
                year = year,
                month = month,
            )
        }
    }
}
