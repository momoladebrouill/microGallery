package studio.lunabee.microgallery.android.remote.datasource

import com.lunabee.lbcore.model.LBResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import studio.lunabee.microgallery.android.data.RemoteStatus
import studio.lunabee.microgallery.android.remote.model.BashRemoteStatus
import studio.lunabee.microgallery.android.remote.service.RootService
import studio.lunabee.microgallery.android.repository.datasource.remote.RemoteStatusDatasource

class RemoteStatusDatasourceImpl(
    private val rootService: RootService,
) : RemoteStatusDatasource {
    override fun fetchStatus(): Flow<RemoteStatus> {
        return rootService
            .fetchStatus()
            .filterIsInstance<LBResult.Success<BashRemoteStatus>>()
            .map { it.successData.toRemoteStatus() }
    }
}
