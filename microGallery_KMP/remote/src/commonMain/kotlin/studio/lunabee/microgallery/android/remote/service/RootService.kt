package studio.lunabee.microgallery.android.remote.service

import io.ktor.client.call.body
import io.ktor.client.request.get
import studio.lunabee.microgallery.android.remote.CoreHttpClient
import studio.lunabee.microgallery.android.remote.model.RemoteMicroElement

class RootService(
    private val coreHttpClient: CoreHttpClient,
) {
    suspend fun fetchRootList(): List<RemoteMicroElement> {
        return coreHttpClient.httpClient.get("/commande/treeJSON").body()
    }
}
