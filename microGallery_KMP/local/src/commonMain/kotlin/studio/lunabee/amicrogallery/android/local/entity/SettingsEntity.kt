package studio.lunabee.amicrogallery.android.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import studio.lunabee.microgallery.android.data.SettingsData

const val SettingsTable = "SettingsTable"

@Entity(
    tableName = SettingsTable,
)
data class SettingsEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: Long = 4871L, // magic number
    @ColumnInfo(name = "ipvsix") val ipv6: String = "2a01:cb1c:82c7:5e00:9f05:da30:3fcf:58ac",
    @ColumnInfo(name = "ipvfour") val ipv4: String = "92.150.239.130",
    @ColumnInfo(name = "viewInHD") val viewInHD: Boolean = true,
    @ColumnInfo(name = "useIpvSix") val useIpv6: Boolean = false,
) {
    fun toSettingsData(): SettingsData {
        return SettingsData(
            ipv4 = ipv4,
            ipv6 = ipv6,
            useIpv6 = useIpv6,
            viewInHD = viewInHD,
        )
    }

    companion object {
        fun fromSettingsData(settingsData: SettingsData): SettingsEntity {
            return SettingsEntity(
                ipv4 = settingsData.ipv4,
                ipv6 = settingsData.ipv6,
                useIpv6 = settingsData.useIpv6,
                viewInHD = settingsData.viewInHD,
            )
        }
    }
}
