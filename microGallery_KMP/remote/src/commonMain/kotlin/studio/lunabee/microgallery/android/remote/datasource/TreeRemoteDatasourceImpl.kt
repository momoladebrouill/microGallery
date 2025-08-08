package studio.lunabee.microgallery.android.remote.datasource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
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
    override suspend fun getYears(): List<MYear> {
        return rootService.fetchYearList()
    }

    override fun getYearDirs(years: List<MYear>): Flow<Directory> {
        return rootService.fetchYears(years).map {
            giveFullNameToFiles(it[0].toData())
        }.filterIsInstance()
    }

    override fun setRemoteBaseUrl(url: String) {
        rootService.initHttpClient(url)
    }
}

fun giveFullNameToFiles(node: Node, path: String = "", year: MYear? = null, month: MMonth? = null): Node {
    return when (node) {
        is Directory -> Directory(
            name = node.name,
            content = node.content.map { child: Node ->
                giveFullNameToFiles(
                    node = child,
                    path = "$path/${node.name}",
                    year = year ?: node.name.substringAfterLast('/'), // if year is not defined, it's a year directory
                    month = if (year != null) node.name else month, // if year is defined but not month, it's a month
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
