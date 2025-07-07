package studio.lunabee.microgallery.android.remote.service

import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import studio.lunabee.microgallery.android.remote.CoreHttpClient
import studio.lunabee.microgallery.android.remote.model.BashRemoteStatus
import studio.lunabee.microgallery.android.remote.model.RemoteMicroElement

class RootService(
    private val coreHttpClient: CoreHttpClient,
) {
    fun fetchRootList(): Flow<List<RemoteMicroElement>> {
        return flow {coreHttpClient.httpClient.get("/commande/treeJSON").body()}
    }

    fun fetchStatus(): Flow<BashRemoteStatus> {
        return flow {coreHttpClient.httpClient.get("/commande/status").body()}
    }
}
