package studio.lunabee.amicrogallery.android.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import studio.lunabee.microgallery.android.data.MicroPicture
import studio.lunabee.microgallery.android.data.Picture
import studio.lunabee.microgallery.android.data.SettingsData
import kotlin.plus

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

    fun toMicroPicture(settingsData: SettingsData, defaultToHighRes: Boolean = true): MicroPicture {
        val data = settingsData

        val (begin0, begin1) =
            if(data.useIpv6)
                Pair("[${data.ipv6}]", data.ipv4)
            else
                Pair(data.ipv4, "[${data.ipv6}]")
        val urlsToTry =
            if(data.viewInHD){
                val (end0, end1) =
                    if (defaultToHighRes)
                        Pair(fullResPath, lowResPath)
                    else
                        Pair( lowResPath, fullResPath)
                listOf(
                    begin0 + end0,
                    begin0 + end1,
                    begin1 + end0,
                    begin1 + end1
                )
            } else {
                listOf(
                    begin0 + lowResPath,
                    begin1 + lowResPath
                )
            }

        val len = urlsToTry.size
        val  beg = "http://"
        return MicroPicture(
            id = id,
            name = name,
            paths = urlsToTry.map {beg + it},
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
