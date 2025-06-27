package studio.lunabee.amicrogallery.android.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import studio.lunabee.microgallery.android.data.Picture

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

    fun toPicture(): Picture {
        return Picture(
            id = id,
            name = name,
            fullResPath = fullResPath,
            lowResPath = lowResPath,
            year = year,
            month = month
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