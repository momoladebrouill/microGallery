package studio.lunabee.microgallery.android.remote.model

import kotlinx.serialization.Serializable
import studio.lunabee.microgallery.android.data.RemoteStatus

@Serializable
data class BashRemoteStatus(
    val temperature: Int,
    val isplugged: Boolean,
    val quantityhr: Int,
    val quantitylr: Int,
) {
    fun toRemoteStatus(): RemoteStatus {
        return RemoteStatus(
            isPlugged = isplugged,
            temperature = temperature,
            quantityHighRes = quantityhr,
            quantityLowRes = quantitylr,
        )
    }
}
