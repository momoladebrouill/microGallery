package studio.lunabee.microgallery.android.remote.model

import kotlinx.serialization.Serializable
import studio.lunabee.amicrogallery.android.error.CoreError
import studio.lunabee.microgallery.android.domain.Directory
import studio.lunabee.microgallery.android.domain.Node
import studio.lunabee.microgallery.android.domain.Picture

@Serializable
data class RemoteMicroElement(
    val type : String,
    val name : String,
    val contents: List<RemoteMicroElement>?
) {
    fun toData() : Node {
        return when(type){
            "directory" -> Directory(
                name = name,
                content = (contents?.map { it.toData()})!!
            )
            "file" -> Picture(
                name = name,
                fullPath = null
            )
            else ->
                throw CoreError("found a new type in request")
        }
    }
}