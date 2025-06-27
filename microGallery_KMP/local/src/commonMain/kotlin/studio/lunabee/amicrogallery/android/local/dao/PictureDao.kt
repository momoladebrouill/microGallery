package studio.lunabee.amicrogallery.android.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import studio.lunabee.amicrogallery.android.local.entity.PhotosTable
import studio.lunabee.amicrogallery.android.local.entity.PictureEntity

@Dao
interface PictureDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPicturesEntities(pictures: List<PictureEntity>)

    @Query("DELETE FROM $PhotosTable")
    suspend fun freshStart()

    @Query("SELECT DISTINCT year FROM $PhotosTable WHERE year <> 'untimed' ")
    suspend fun getYears() : List<String>

    @Query("SELECT DISTINCT month FROM $PhotosTable WHERE year = :year")
    suspend fun getMonthsInYear(year : String) : List<String>

    @Query("SELECT * FROM $PhotosTable WHERE year = :year AND month = :month")
    suspend fun getPicturesInMonth(year : String,month : String) : List<PictureEntity>

    @Query("SELECT * FROM $PhotosTable WHERE year = 'untimed' AND month IS NULL")
    suspend fun getPicturesUntimed() : List<PictureEntity>

    @Query("SELECT * FROM $PhotosTable WHERE id = :id")
    suspend fun getPictureEntityFromId(id : Long) : PictureEntity
}