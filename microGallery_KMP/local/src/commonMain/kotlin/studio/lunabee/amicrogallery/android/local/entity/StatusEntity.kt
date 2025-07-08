package studio.lunabee.amicrogallery.android.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import studio.lunabee.microgallery.android.data.RemoteStatus

const val StatusTable = "StatusTable"
const val MagickNumber = 4871L

@Entity(
    tableName = StatusTable,
)
data class StatusEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: Long = MagickNumber,
    @ColumnInfo(name = "temperature") val temp: Int,
    @ColumnInfo(name = "photoshr") val photosHR: Int,
    @ColumnInfo(name = "photoslr") val photosLR: Int,
    @ColumnInfo(name = "isdiskplugged") val diskPlugged: Boolean,
) {
    fun toRemoteStatus(): RemoteStatus {
        return RemoteStatus(
            isPlugged = diskPlugged,
            temperature = temp,
            quantityHighRes = photosHR,
            quantityLowRes = photosLR,
        )
    }

    companion object {
        fun fromRemoteStatus(remoteStatus: RemoteStatus): StatusEntity {
            return StatusEntity(
                id = magickNumber,
                temp = remoteStatus.temperature,
                photosHR = remoteStatus.quantityHighRes,
                photosLR = remoteStatus.quantityLowRes,
                diskPlugged = remoteStatus.isPlugged,
            )
        }
    }
}
