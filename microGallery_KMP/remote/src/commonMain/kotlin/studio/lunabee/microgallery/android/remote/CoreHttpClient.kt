package studio.lunabee.microgallery.android.remote

import com.lunabee.ktor.LBKtorExceptionHandler
import com.lunabee.ktor.LBKtorJson
import com.lunabee.ktor.LBKtorKermit
import io.ktor.client.HttpClient
import studio.lunabee.amicrogallery.android.error.CoreError

class CoreHttpClient {

    lateinit var httpClient: HttpClient

    fun setHttpClient(baseRemoteUrl: String) {
        httpClient = HttpClient {
            install(LBKtorExceptionHandler) {
                mapErr = { CoreError(it.toString()) }
            }
            install(LBKtorJson) {
                url = "http://$baseRemoteUrl"
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
                explicitNulls = false
            }
            install(LBKtorKermit) {
                logLevel = LBKtorKermit.LogLevel.Headers
            }
        }
    }
}
