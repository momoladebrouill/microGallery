package studio.lunabee.microgallery.android.repository.impl

import studio.lunabee.microgallery.android.domain.Node
import studio.lunabee.microgallery.android.domain.calendar.CalendarRepository
import studio.lunabee.microgallery.android.repository.datasource.remote.TreeRemoteDatasource

class CalendarRepositoryImpl(
    private val treeRemoteDatasource: TreeRemoteDatasource,
) : CalendarRepository {

    override suspend fun fetchRootNode(): Node {
        return treeRemoteDatasource.fetchRoot()
    }
}
