package studio.lunabee.microgallery.android.remote.service

import com.lunabee.lbcore.model.LBResult
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import studio.lunabee.amicrogallery.android.error.CoreError
import studio.lunabee.microgallery.android.data.MYear
import studio.lunabee.microgallery.android.remote.CoreHttpClient
import studio.lunabee.microgallery.android.remote.model.BashRemoteStatus
import studio.lunabee.microgallery.android.remote.model.RemoteMicroElement

class RootService(
    private val coreHttpClient: CoreHttpClient,
) {
    suspend fun fetchYearList(): List<MYear> {
        return coreHttpClient.httpClient.get("/commande/treeJSON?all=True").body()
    }

    fun initHttpClient(url: String) {
        coreHttpClient.setHttpClient(url)
    }

    fun fetchYears(yearList: List<MYear>): Flow<List<RemoteMicroElement>> {
        return flow {
            yearList.forEach {
                emit(coreHttpClient.httpClient.get("/commande/treeJSON?year=$it").body())
            }
        }
    }

    fun fetchStatus(): Flow<LBResult<BashRemoteStatus>> {
        return flow {
            emit(
                CoreError.runCatching {
                    coreHttpClient.httpClient.get("/commande/status").body()
                },
            )
        }
    }
}
