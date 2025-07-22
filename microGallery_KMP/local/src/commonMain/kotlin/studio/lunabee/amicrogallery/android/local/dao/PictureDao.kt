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

    @Query("SELECT DISTINCT month FROM $PhotosTable WHERE year = :year")
    fun monthsInYear(year: String): Flow<List<String>>

    @Query("SELECT * FROM $PhotosTable WHERE year = :year AND month = :month")
    fun picturesInMonth(year: String, month: String): Flow<List<PictureEntity>>

    @Query("SELECT * FROM $PhotosTable WHERE year = 'untimed' AND month IS NULL")
    fun picturesUntimed(): Flow<List<PictureEntity>>

    @Query("SELECT * FROM $PhotosTable WHERE id = :id")
    fun pictureEntityFromId(id: Long): Flow<PictureEntity>

    @Query("SELECT DISTINCT year FROM $PhotosTable WHERE year <> 'untimed'")
    fun getYears(): Flow<List<String>>

    @Query("SELECT Count(id) FROM $PhotosTable WHERE year=:year")
    fun getQtyInYear(year: String): Flow<Int>

    @Query("SELECT * FROM $PhotosTable WHERE year = :year ORDER BY RANDOM()")
    fun getRandomPictureInYear(year: String): Flow<PictureEntity>

    @Query("SELECT scale FROM $PhotosTable WHERE id=:id")
    suspend fun getOrderById(id: Long): Float

    @Query("SELECT * FROM $PhotosTable WHERE scale = (SELECT MAX(scale) FROM $PhotosTable WHERE scale < :scale)")
    suspend fun getFirstPictureBefore(scale: Float): PictureEntity

    @Query("SELECT * FROM $PhotosTable WHERE scale = (SELECT MIN(scale) FROM $PhotosTable WHERE scale > :scale)")
    suspend fun getFirstPictureAfter(scale: Float): PictureEntity

    @Query("SELECT EXISTS(SELECT * FROM PhotosTable LIMIT 1) FROM PhotosTable LIMIT 1")
    suspend fun isThereAnyPicture(): Boolean
}
