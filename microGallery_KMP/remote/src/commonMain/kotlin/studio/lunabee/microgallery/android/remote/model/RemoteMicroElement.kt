package studio.lunabee.microgallery.android.remote.model

import kotlinx.serialization.Serializable
import studio.lunabee.amicrogallery.android.error.CoreError
import studio.lunabee.microgallery.android.data.Directory
import studio.lunabee.microgallery.android.data.Node
import studio.lunabee.microgallery.android.data.Picture

@Serializable
data class RemoteMicroElement(
    val type: String,
    val name: String,
    val contents: List<RemoteMicroElement>?,
) {
    fun toData(): Node {
        return when (type) {
            "directory" -> Directory(
                id = 5L,
                name = name,
                content = (contents?.map { it.toData() })!!,
            )
            "file" -> Picture(
                id = 4L,
                name = name,
                fullResPath = null,
                lowResPath = null,
            )
            else ->
                throw CoreError("found a new type in request")
        }
    }
}
