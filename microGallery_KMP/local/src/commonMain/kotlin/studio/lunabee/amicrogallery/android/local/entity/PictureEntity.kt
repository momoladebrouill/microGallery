package studio.lunabee.amicrogallery.android.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import studio.lunabee.microgallery.android.data.MicroPicture
import studio.lunabee.microgallery.android.data.Picture
import studio.lunabee.microgallery.android.data.SettingsData

const val PhotosTable = "PhotosTable"

@Entity(
    tableName = PhotosTable,
)
data class PictureEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "fullResPath") val fullResPath: String?,
    @ColumnInfo(name = "lowResPath") val lowResPath: String?,
    @ColumnInfo(name = "year") val year: String?,
    @ColumnInfo(name = "month") val month: String?,
) {
    fun toMicroPicture(settingsData: SettingsData): MicroPicture {
        return MicroPicture(
            id = id,
            name = name,
            lowResPaths = getUrl(settingsData, false, picture = this),
            highResPaths = getUrl(settingsData, true, picture = this),
            year = year ?: "",
            month = month ?: "",
        )
    }

    companion object {
        fun fromPicture(picture: Picture): PictureEntity {
            return PictureEntity(
                name = picture.name,
                fullResPath = picture.fullResPath,
                lowResPath = picture.lowResPath,
                year = picture.year,
                month = picture.month,
            )
        }
    }
}

fun getUrl(data: SettingsData, defaultToHighRes: Boolean, picture: PictureEntity): List<String> {
    val (begin0, begin1) =
        if (data.useIpv6) {
            Pair("http://[${data.ipv6}]", "http://${data.ipv4}")
        } else {
            Pair("http://${data.ipv4}", "http://[${data.ipv6}]")
        }

    if (data.viewInHD) {
        val (end0, end1) =
            if (defaultToHighRes) {
                Pair(picture.fullResPath, picture.lowResPath)
            } else {
                Pair(picture.lowResPath, picture.fullResPath)
            }
        return listOf(
            begin0 + end0,
            begin0 + end1,
            begin1 + end0,
            begin1 + end1,
        )
    } else {
        return listOf(
            begin0 + picture.lowResPath,
            begin1 + picture.lowResPath,
        )
    }
}
